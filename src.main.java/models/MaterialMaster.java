package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name=JpaConst.TABLE_MATM)
@NamedQueries({
    @NamedQuery(
            name=JpaConst.Q_MATM_GET_ALL,
            query=JpaConst.Q_MATM_GET_ALL_DEF),
    @NamedQuery(
            name=JpaConst.Q_MATM_COUNT,
            query=JpaConst.Q_MATM_COUNT_DEF),
    @NamedQuery(
            name=JpaConst.Q_MATM_COUNT_RESISTERED_BY_NAME,
            query=JpaConst.Q_MATM_COUNT_RESISTERED_BY_NAME_DEF),
})

@Getter//全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class MaterialMaster {

    /**
     * id
     */
    @Id
    @Column(name=JpaConst.MATM_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;


    /**
     * 材料名
     */
    @Column(name=JpaConst.MATM_COL_NAME,nullable=false)
    private String name;

    /**
     * 単位
     */
    @Column(name=JpaConst.MATM_COL_UNIT,nullable=false)
    private String unit;




}
