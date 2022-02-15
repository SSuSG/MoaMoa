package SG.MoaMoa.domain;


import SG.MoaMoa.dto.BoardDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor @Builder @NoArgsConstructor
public class Board extends TimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String writer; //글 작성자
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BoardDto toDto(){
        BoardDto build = BoardDto.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .createdDate(getCreateDate())
                .modifiedDate(getModifiedDate())
                .build();
        return build;
    }


    //==비즈니스 로직==//
    public void updateBoard(BoardDto boardDto){
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }

}
