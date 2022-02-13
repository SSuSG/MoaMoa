package SG.MoaMoa.service;


import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.Image;
import SG.MoaMoa.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${image.dir}")
    private String imageDir;

    public String getFullPath(String imageName) {
        return imageDir + imageName;
    }

    //이미지 여러개를 실제로 저장
    public List<Image> storeImages(List<MultipartFile> multipartFiles) throws IOException {
        List<Image> storeImageResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()){
                storeImageResult.add(storeImage(multipartFile , false) );
            }
        }
        return storeImageResult;
    }


    //이미지 하나를 실제로 저장하고 Image객체로 바꾸어 저장
    public Image storeImage(MultipartFile multipartFile , boolean isMain) throws IOException
    {
        if(multipartFile.isEmpty())
            return null;

        log.info("ImageService : storeImage");
        String originalImageName = multipartFile.getOriginalFilename();
        String storeImageName = createStoreFileName(originalImageName);
        multipartFile.transferTo(new File(getFullPath(storeImageName)));

        return Image.builder().uploadImageName(originalImageName).storeImageName(storeImageName).isMain(isMain).build();
    }

    //서버 내부에서 관리하는 이미지명은 유일한 이름을 생성하는 UUID 를 사용해서 충돌하지 않도록 한다.
    private String createStoreFileName(String originalImageName) {
        String ext = extractExt(originalImageName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자를 별도로 추출해서 서버 내부에서 관리하는 이미지명에도 붙여준다.
    private String extractExt(String originalImageName) {
        int pos = originalImageName.lastIndexOf(".");
        return originalImageName.substring(pos + 1);
    }


}
