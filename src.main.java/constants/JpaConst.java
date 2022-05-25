package constants;


/**
 * DB関連の項目地を定義するインターフェース
 * @author nyz54
 *
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME="order_quantity_list";

    //データ取得件数の最大値
    int ROW_PER_PAGE=15;

    String TABLE_USE="users";

    //関係者テーブル
    String USE_COL_ID="id";
    String USE_COL_CODE="code";
    String USE_COL_NAME="name";
    String USE_COL_PASS="password";
    String USE_COL_ADMIN_FLAG="admin_flag";
    String USE_COL_DELETE_FLAG="delete_flag";

    int ROLE_ADMIN=1;
    int ROLE_GENERAL=0;
    int USE_DEL_TRUE=1;
    int USE_DEL_FALSE=0;


    //材料テーブル
    String MAT_COL_ID="id";
    String MAT_COL_NAME="name";
    String MAT_COL_UNIT="unit";
    String MAT_COL_COO="cooking_id";

    //料理テーブル
    String COO_COL_ID="id";
    String COO_COL_NAME="name";

    //Entity名
    String ENTITY_USE="user";//利用者
    String ENTITY_MAT="material";//材料
    String ENTITY_COO="cooking";

    String JPQL_PARM_CODE="code";
    String JPQL_PARM_PASSWORD="password";
    String JPQL_PARM_USER="user";



    //NamedQueryのnameとquery
    //全ての利用者をidの降順に取得する
    String Q_USE_GET_ALL=ENTITY_USE+".getAll";
    String Q_USE_GET_ALL_DEF="SELECT FROM User AS u ORDER BY u.id DESC";//query
    //全ての利用者の件数を取得する
    String Q_USE_COUNT=ENTITY_USE+".count";
    String Q_USE_COUNT_DEF="SELECT COUNT(u) FROM User AS u";
    //ユーザー番号とハッシュ化済パスワードを条件に未削除の利用者を取得する
    String Q_USE_GET_BY_CODE_AND_PASS=ENTITY_USE+".getByCodeAndPass";
    String Q_USE_GET_BY_CODE_AND_PASS_DEF="SELECT u FROM User AS u WHERE u.deleteFlag=0 AND u.code=:"+JPQL_PARM_CODE+"AND u.password= :" + JPQL_PARM_PASSWORD;
    //指定したユーザー番号を保持する利用者の件数を取得する
    String Q_USE_COUNT_RESISTERED_BY_CODE=ENTITY_USE+".countRegisteredByCode";
    String Q_USE_COUNT_RESISTERED_BY_CODE_DEF="SELECT COUNT(u) FROM User AS u WHERE u.code=:"+JPQL_PARM_CODE;
    //全ての材料をidの降順に取得する
    String Q_MAT_GET_ALL=ENTITY_MAT+".getAll";
    String Q_MAT_GET_ALL_DEF="SELECT FROM Material AS m ORDER BY m.id DESC";
    //全ての材料の件数を取得する
    String Q_MAT_COUNT=ENTITY_MAT+".count";
    String Q_MAT_COUNT_DEF="SELECT COUNT(m) FROM Material AS m";
    //全ての料理をidの降順に取得する
    String Q_COO_GET_ALL=ENTITY_COO+".getAll";
    String Q_COO_GET_ALL_DEF="SELECT FROM Cooking AS c ORDER BY c.id  DESC";
   //全ての料理の件数を取得する
    String Q_COO_COUNT=ENTITY_COO+".count";
    String Q_COO_COUNT_DEF="SELECT COUNT(c) FROM Cooking AS c";


}
