package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * JPQLのDISTINCTの結果を受け取るDTOモデル
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MaterialDistinct {


    private Integer materialMasterId;

    private Integer cookingId;


}
