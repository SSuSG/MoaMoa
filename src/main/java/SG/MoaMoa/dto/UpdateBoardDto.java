package SG.MoaMoa.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBoardDto {

    private Long id;
    private String title;
    private String content;
}
