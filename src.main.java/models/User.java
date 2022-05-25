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

/**
 *
 * 利用者データのDTOモデル
 *
 */
@Table(name=JpaConst.TABLE_USE)
@NamedQueries({
    @NamedQuery(
            name=JpaConst.Q_USE_GET_ALL,
            query=JpaConst.Q_USE_GET_ALL_DEF),
    @NamedQuery(
            name=JpaConst.Q_USE_COUNT,
            query=JpaConst.Q_USE_COUNT_DEF),
    @NamedQuery(
            name=JpaConst.Q_USE_GET_BY_CODE_AND_PASS,
            query=JpaConst.Q_USE_GET_BY_CODE_AND_PASS_DEF),
    @NamedQuery(
            name=JpaConst.Q_USE_COUNT_RESISTERED_BY_CODE,
            query=JpaConst.Q_USE_COUNT_RESISTERED_BY_CODE_DEF),
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class User {

    /**
     * id
     */
    @Id
    @Column(name=JpaConst.USE_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * ユーザー番号
     */
    @Column(name=JpaConst.USE_COL_CODE,nullable=false,unique=true)
    private String code;

    /**
     * 氏名
     */
    @Column(name=JpaConst.USE_COL_NAME,nullable=false)
    private String name;

    /**
     * パスワード
     */
    @Column(name=JpaConst.USE_COL_PASS,length=64,nullable=false)
    private String password;

    /**
     * 作成者権限があるかどうか(閲覧者:0,作成者:1)
     */
    @Column(name=JpaConst.USE_COL_AUTHOR_FLAG,nullable=false)
    private Integer authorFlag;

    /**
     * 削除された利用者かどうか(現役:0、削除済み:1)
     */
    @Column(name=JpaConst.USE_COL_DELETE_FLAG,nullable=false)
    private Integer deleteFlag;

}
