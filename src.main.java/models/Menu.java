package models;

import java.time.LocalDate;

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

/**
 * メニューデータのDTOモデル
 *
 */
@Table(name=JpaConst.TABLE_MENU)
@NamedQueries({
    @NamedQuery(
            name=JpaConst.Q_MEN_GET_ALL,
            query=JpaConst.Q_MEN_GET_ALL_DEF),
    @NamedQuery(
            name=JpaConst.Q_MEN_COUNT,
            query=JpaConst.Q_MEN_COUNT_DEF),
    @NamedQuery(
            name=JpaConst.Q_MEN_GET_BY_MAXID,
            query=JpaConst.Q_MEN_GET_BY_MAXID_DEF)

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Menu {

    /**
     * id
     */
    @Id
    @Column(name=JpaConst.MENU_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     *メニューの開始日
     */
    @Column(name=JpaConst.MENU_COL_START_DATE,nullable=false)
    private LocalDate startDate;

    /**
     * メニューの終了日
     */
    @Column(name=JpaConst.MENU_COL_END_DATE,nullable=false)
    private LocalDate endDate;



}
