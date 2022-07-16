package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 料理テーブルのDTOモデル(CookingViewに量を加えた)
 *
 *
 */
@Table(name=JpaConst.TABLE_COS)
@NamedQueries({
    @NamedQuery(
            name=JpaConst.Q_COS_GET_ALL,
            query=JpaConst.Q_COS_GET_ALL_DEF),
    @NamedQuery(
            name=JpaConst.Q_COS_GET_BY_MENU_ID,
            query=JpaConst.Q_COS_GET_BY_MENU_ID_DEF)

})

@Getter//全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class CookingSlave {

    /**
     * id
     */
    @Id
    @Column(name=JpaConst.COS_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;


    /**
     * メニューのid
     */
    @ManyToOne
    @JoinColumn(name=JpaConst.COS_COL_MEN,nullable=false)
    private Menu menu;

    /**
     * メニューで使用する料理のid
     */
    @ManyToOne
    @JoinColumn(name=JpaConst.COS_COL_COO,nullable=false)
    private Cooking cooking;


    /**
     * 料理の数
     */
    @Column(name=JpaConst.COS_COL_AMOUNT)
    private Integer amount;

    /**
     * 時間帯
     */
    @Column(name=JpaConst.COS_COL_TIME)
    private String timeZone;



}
