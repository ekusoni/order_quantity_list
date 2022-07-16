package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPQLのSUMの計算結果を受け取るDTOモデル
 *
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MaterialGroupBy {





    private Integer materialId;


    private Integer cookingId;


    private Long amount;

}
