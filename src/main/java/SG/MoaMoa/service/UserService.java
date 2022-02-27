package SG.MoaMoa.service;


import SG.MoaMoa.domain.*;
import SG.MoaMoa.dto.*;
import SG.MoaMoa.repository.FundingRepository;
import SG.MoaMoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final JavaMailSender emailSender;
    private final EntityManager em;

    //회원가입
    @Transactional
    public String join(UserDto userDto) throws Exception {
        log.info("UserService : join");

        //인증키를 만들고 이메일 보내기
        String email = userDto.getEmail();
        String authenticationKey = createKey();
        sendSimpleMessage(email,authenticationKey);

        User user = userDto.toEntity();
        //회원가입시 초기화할 정보들
        user.join(0,authenticationKey,RoleType.ASSOCIATE);
        userRepository.save(user);
        return "success";
    }

    //로그인
    public User login(String loginId , String password){

        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    //회원가입시 보낼 메세지 작성
    private MimeMessage createMessage(String to, String authenticationKey)throws Exception{
        log.info("UserService : createMessage1");
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+authenticationKey);
        MimeMessage  message = emailSender.createMimeMessage();
        log.info("UserService : createMessage2");
        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("Ace" + "회원가입 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 MoaMoaFunding입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= authenticationKey+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        //message.setFrom(new InternetAddress("properties email!","Ace"));//보내는 사람
        log.info("UserService : createMessage3");
        return message;
    }

    //회원가입시 인증키 생성
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    //회원가입시 이메일전송
    public void sendSimpleMessage(String to,String authenticationKey)throws Exception {
        log.info("UserService : sendSimpleMessage");
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to,authenticationKey);

        try{//예외처리
            log.info("UserService : sendSimpleMessage1");
            emailSender.send(message);
            log.info("UserService : sendSimpleMessage2");
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    //회원가입후 이메일로 보내진 인증키 인증시 준회원 -> 정회원
    @Transactional
    public boolean IsEqualAuthenticationKey(Long id,String authenticationKey){
        log.info("UserService : IsEqualAuthenticationKey");
        User user = userRepository.findById(id).get();
        log.info("authenticationKey : {}" , authenticationKey);
        if(user.getAuthenticationKey().equals(authenticationKey)){
            log.info("UserService : updateRoleTypeForJoin");
            user.updateRoleTypeForJoin();
            return true;
        }
        return false;
    }

    //아이디 찾기
    public String findLoginId(FindLoginIdDto findLoginIdDto){
        Optional<User> findUser = userRepository.findByNameAndEmail(findLoginIdDto.getName(), findLoginIdDto.getEmail());
        if(findUser.isPresent()){
            return findUser.get().getLoginId();
        }else{ //존재하지 않으면
            //throw new IllegalStateException("일치하는 회원의 아이디가 없습니다.");
            return "false";
        }
    }


    //비밀번호찾기
    public String findPassword(FindPasswordDto findPasswordDto){
        Optional<User> findUser = userRepository.findByNameAndEmailAndLoginId(
                findPasswordDto.getName(), findPasswordDto.getEmail(), findPasswordDto.getLoginId());
        if(findUser.isPresent()){
            return findUser.get().getPassword();
        }else{ //존재하지 않으면
            //throw new IllegalStateException("일치하는 회원의 비밀번호를 찾을수없습니다.");
            return "false";
        }
    }


    //아이디 중복체크
    public boolean checkLoginIdDuplicate(String loginId) {
        //true -> 아이디중복 , false -> 아이디중복 없음
        return userRepository.existsByLoginId(loginId);
    }

    //회원중복체크 -> 이름+이메일
    public boolean validateDuplicateUser(String name , String email){
        Optional<User> findUser = userRepository.findByNameAndEmail(name, email);
        if(findUser.isPresent()){
            return true;
            //throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        return false;
    }

    //내정보 페이지에 펀딩참여내역 제공
    public List<FundingDto> getMyFundingList(Long userId) {
        List<UserFunding> userFundingList = userRepository.findById(userId).get().getUserFundings();
        return userFundingList.stream()
                .map( userFunding -> userFunding.getFunding().toDto())
                .collect(Collectors.toList());
    }

    //돈 충전
    @Transactional
    public void chargeMoney(Long id, int money) {
        User user = userRepository.findById(id).get();
        user.chargeMoney(money);
    }

    //내 정보 return
    public UserDto getMyInfo(Long userId) {
        return userRepository.findById(userId).get().toDto();
    }

    //구독하기!!
    @Transactional
    public boolean subscription(Long userId) {
        User user = userRepository.findById(userId).get();

        //구독비(5000)가 없으면 구독 실패
        if(user.getMoney() < 5000){
            return false;
        }
        //유저의 roleType update , 돈 차감
        user.updateRoleTypeForSubscription();
        user.spendMoney(5000);

        //연관관계 메소드
        Subscription subscription = Subscription.createSubscription();
        user.setSubscription(subscription);
        em.flush();

        return true;
    }

    public User getLoginUser(Long userId) {
        return userRepository.findById(userId).get();
    }
}
