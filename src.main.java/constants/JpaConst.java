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
    int ROW_PER_PAGE=15; //1ページに表示するレコードの数


    //利用者テーブル
    String TABLE_USE="users"; //テーブル名
    //利用者テーブルカラム
    String USE_COL_ID="id"; //id
    String USE_COL_CODE="code"; //利用者番号
    String USE_COL_NAME="name"; //氏名
    String USE_COL_PASS="password"; //パスワード
    String USE_COL_AUTHOR_FLAG="author_flag"; //作成者権限
    String USE_COL_DELETE_FLAG="delete_flag"; //削除フラグ


    int ROLE_AUTHOR=1;//作成者権限ON(作成者)
    int ROLE_VIEWER=0;//作成者権限OFF(閲覧者)
    int USE_DEL_TRUE=1;//削除フラグON(削除済み)
    int USE_DEL_FALSE=0;//削除フラグOFF(現役)

    //材料マスタ(材料名、単位)テーブル
    String TABLE_MATM="material_masters"; //テーブル名
    //材料マスタテーブルカラム
    String MATM_COL_ID="id"; //id
    String MATM_COL_NAME="name"; //材料名
    String MATM_COL_UNIT="unit"; //材料の単位

    //材料(量)テーブル
    String TABLE_MAT="materials";
    //材料テーブルカラム
    String MAT_COL_ID="id";//id
    String MAT_COL_MATM="material_master_id";//材料マスタのid
    String MAT_COL_COO="cooking_id";//材料を使用する料理
    String MAT_COL_AMOUNT="amount";//量

    //料理テーブル
    String TABLE_COO="cookings";
    //料理テーブルカラム
    String COO_COL_ID="id";
    String COO_COL_NAME="name";
    String COO_COL_AMOUNT="amount";

    //料理(量)テーブル
    String TABLE_COS="cooking_slaves";
    //料理テーブルカラム
    String COS_COL_ID="id";
    String COS_COL_MEN="menu_id";
    String COS_COL_COO="cooking_id";
    String COS_COL_AMOUNT="amount";
    String COS_COL_TIME="time_zone";

    //メニューテーブル
    String TABLE_MENU="menus";
    //メニューカラム
    String MENU_COL_ID="id";//id
    String MENU_COL_START_DATE="start_date";//メニューの開始日
    String MENU_COL_END_DATE="end_date";//メニューの終了日

    //Entity名
    String ENTITY_USE="user";//利用者
    String ENTITY_MATM="materialMaster";//材料(材料名と単位)
    String ENTITY_MAT="material";//材料(量)
    String ENTITY_COO="cooking";//料理
    String ENTITY_COS="cookingSlave";//料理(量)
    String ENTITY_MEN="menu";//メニュー

    //JPQL内のパラメータ
    String JPQL_PARM_CODE="code";//利用者番号
    String JPQL_PARM_PASSWORD="password";//パスワード
    String JPQL_PARM_USER="user";//利用者
    String JPQL_PARM_NAME="name";//材料名
    String JPQL_PARM_UNIT="unit";//単位
    String JPQL_PARM_ID="id";//id



    //NamedQueryのnameとquery
    //全ての利用者をidの降順に取得する
    String Q_USE_GET_ALL=ENTITY_USE+".getAll";
    String Q_USE_GET_ALL_DEF="SELECT u FROM User AS u ORDER BY u.id DESC";//query
    //全ての利用者の件数を取得する
    String Q_USE_COUNT=ENTITY_USE+".count";
    String Q_USE_COUNT_DEF="SELECT COUNT(u) FROM User AS u";
    //ユーザー番号とハッシュ化済パスワードを条件に未削除の利用者を取得する
    String Q_USE_GET_BY_CODE_AND_PASS=ENTITY_USE+".getByCodeAndPass";
    String Q_USE_GET_BY_CODE_AND_PASS_DEF="SELECT u FROM User AS u WHERE u.deleteFlag=0 AND u.code=:" + JPQL_PARM_CODE + " AND u.password= :" + JPQL_PARM_PASSWORD;
    //指定したユーザー番号を保持する利用者の件数を取得する
    String Q_USE_COUNT_RESISTERED_BY_CODE=ENTITY_USE+".countRegisteredByCode";
    String Q_USE_COUNT_RESISTERED_BY_CODE_DEF="SELECT COUNT(u) FROM User AS u WHERE u.code=:"+JPQL_PARM_CODE;
    //全ての材料をidの降順に取得する
    String Q_MATM_GET_ALL=ENTITY_MATM+".getAll";
    String Q_MATM_GET_ALL_DEF="SELECT mm FROM MaterialMaster AS mm ORDER BY mm.id DESC";
    //全ての材料の件数を取得する
    String Q_MATM_COUNT=ENTITY_MATM+".count";
    String Q_MATM_COUNT_DEF="SELECT COUNT(mm) FROM MaterialMaster AS mm";
    //指定した材料名を保持する材料の件数を取得する
    String Q_MATM_COUNT_RESISTERED_BY_NAME=ENTITY_MATM+".countRegisteredByCode";
    String Q_MATM_COUNT_RESISTERED_BY_NAME_DEF="SELECT COUNT(mm) FROM MaterialMaster AS mm WHERE mm.name=:"+JPQL_PARM_NAME;
    //指定した材料名を保持する材料をidの降順に取得する
    String Q_MATM_SEARCH_BY_NAME=ENTITY_MATM+"getByName";
    String Q_MATM_SEARCH_BY_NAME_DEF="SELECT mm FROM MaterialMaster AS mm WHERE mm.name LIKE :"+JPQL_PARM_NAME+" ORDER BY mm.id DESC";
    //指定した単位を保持する材料をidの降順に取得する
    String Q_MATM_SEARCH_BY_UNIT=ENTITY_MATM+"getByunit";
    String Q_MATM_SEARCH_BY_UNIT_DEF="SELECT mm FROM MaterialMaster AS mm WHERE mm.unit LIKE :"+JPQL_PARM_UNIT+" ORDER BY mm.id DESC";
    //全ての材料(量)をidの昇順に取得する
    String Q_MAT_GET_ALL=ENTITY_MAT+".getAll";
    String Q_MAT_GET_ALL_DEF="SELECT m FROM Material AS m ORDER BY m.id";
    //全ての料理をidの降順に取得する
    String Q_COO_GET_ALL=ENTITY_COO+".getAll";
    String Q_COO_GET_ALL_DEF="SELECT c FROM Cooking AS c ORDER BY c.id  DESC";
   //全ての料理の件数を取得する
    String Q_COO_COUNT=ENTITY_COO+".count";
    String Q_COO_COUNT_DEF="SELECT COUNT(c) FROM Cooking AS c";
    //指定した料理名を保持する料理の件数を取得する
    String Q_COO_COUNT_RESISTERED_BY_NAME=ENTITY_COO+".countRegisteredByName";
    String Q_COO_COUNT_RESISTERED_BY_NAME_DEF="SELECT COUNT(c) FROM Cooking AS c WHERE c.name=:"+JPQL_PARM_NAME;
    //指定した料理名を条件に料理を取得する
    String Q_COO_GET_BY_NAME=ENTITY_COO+".getByName";
    String Q_COO_GET_BY_NAME_DEF="SELECT c FROM Cooking AS c WHERE c.name=:"+JPQL_PARM_NAME;
    //全ての料理(量)をidの昇順に取得する
    String Q_COS_GET_ALL=ENTITY_COS+".getAll";
    String Q_COS_GET_ALL_DEF="SELECT cs FROM CookingSlave AS cs ORDER BY cs.id";
    //指定したメニューidを持つ料理(量)を昇順に取得する。
    String Q_COS_GET_BY_MENU_ID=ENTITY_COS+"getByMenuId";
    String Q_COS_GET_BY_MENU_ID_DEF="SELECT cs FROM CookingSlave AS cs WHERE cs.menu.id=:"+JPQL_PARM_ID;
    //全てのメニューをidの降順に取得する
    String Q_MEN_GET_ALL=ENTITY_MEN+".getAll";
    String Q_MEN_GET_ALL_DEF="SELECT m FROM Menu AS m ORDER BY m.id DESC";
    //全てのメニューの件数を取得する
    String Q_MEN_COUNT=ENTITY_MEN+".count";
    String Q_MEN_COUNT_DEF="SELECT COUNT(m) FROM Menu AS m";
    //メニューのidが一番大きいのを取得する
    String Q_MEN_GET_BY_MAXID=ENTITY_MEN+".getById";
    String Q_MEN_GET_BY_MAXID_DEF="SELECT m FROM Menu AS m WHERE m.id=(SELECT MAX(m.id) From Menu AS m)";



}
