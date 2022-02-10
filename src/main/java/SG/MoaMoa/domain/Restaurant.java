package SG.MoaMoa.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    private String restaurantName;

    @Lob
    private String introduction;

    @Lob
    private String information;

    @Lob
    private String notice;

    /*
    @OneToOne(mappedBy = "restaurant" , fetch = FetchType.LAZY)
    private Funding funding;
     */

    @Builder
    public Restaurant(Long id , String restaurantName , String introduction , String information ,String notice){
        this.id = id;
        this.restaurantName = restaurantName;
        this.information = information;
        this.introduction = introduction;
        this.notice = notice;

    }

}
