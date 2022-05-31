package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.MaterialView;
import constants.MessageConst;
import services.MaterialService;

/**
 *材料インスタンスに設定されている値のバリテーションを行うクラス
 */
public class MaterialValidator {

    /**
     * 材料インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元serviceクラスのインスタンス
     * @param mv MaterialViewのインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(MaterialService service,MaterialView mv){
        List<String> errors=new ArrayList<String>();

        //材料名のチェック
        String nameError= validateName(service,mv.getName());
        if(!nameError.equals("")) {
            errors.add(nameError);
        }
        //単位のチェック
        String unitError =validateUnit(mv.getUnit());
        if(!unitError.equals("")) {
            errors.add(unitError);
        }

        return errors;
    }

    /**
     * 材料名の入力チェックを行い、エラーメッセージを返却
     * @param service MaterialServiceのインスタンス
     * @param name 材料名
     */
    private static String validateName(MaterialService service, String name) {

        //入力値がなければエラーメッセージを返却
        if(name==null || name.equals("")) {
            return MessageConst.E_MAT_NONAME.getMessage();
        }

        long materialsCount = isDuplicateMaterial(service,name);

        //同一材料名が既に登録されている場合はエラーメッセージを返却
        if(materialsCount >0) {
            return MessageConst.E_MAT_NAME_EXIST.getMessage();
        }

        //エラーが無い場合は空文字を返却
        return "";
    }

    /**
     * @param service MaterialServiceのインスタンス
     * @param name 材料名
     * @return 材料テーブルに登録されている同一材料名のデータの件数
     */
    private static long isDuplicateMaterial(MaterialService service,String name) {

        long materialsCount=service.countByName(name);
        return materialsCount;
    }

    /**
     * 単位の入力チェックを行い、エラーメッセージを返却
     * @param unit 単位
     * return エラーメッセージ
     */
    private static String validateUnit(String unit) {

        if(unit==null || unit.equals("")) {
            return MessageConst.E_MAT_NOUNIT.getMessage();
        }

        //入力値がある場合は空文字を返却する
        return "";
    }


}
