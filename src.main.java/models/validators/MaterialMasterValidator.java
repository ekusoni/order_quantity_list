package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.MaterialMasterView;
import constants.MessageConst;
import services.MaterialMasterService;

/**
 * 材料インスタンスに設定されている値のバリデーションを行うクラス
 */
public class MaterialMasterValidator {

    /**
     * 材料インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元serviceクラスのインスタンス
     * @param mmv MaterialViewのインスタンス
     * @param nameDuplicateCheckFlag 材料名の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */

    public static List<String> validate(MaterialMasterService service, MaterialMasterView mmv,
            Boolean nameDuplicateCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //材料名のチェック
        String nameError = validateName(service, mmv.getName(), nameDuplicateCheckFlag);
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //単位のチェック
        String unitError = validateUnit(mmv.getUnit());
        if (!unitError.equals("")) {
            errors.add(unitError);
        }

        return errors;

    }

    /**
     * 材料名の入力チェックを行い、エラーメッセージを返却
     * @param service MaterialMasterServiceのインスタンス
     * @param name 材料名
     */
    private static String validateName(MaterialMasterService service, String name, Boolean nameDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却する
        if (name == null || name.equals("")) {
            return MessageConst.E_MATM_NONAME.getMessage();
        }

        if (nameDuplicateCheckFlag) {

            //材料名の重複チェックを実施
            long materialmastersCount = isDuplicateMaterialMaster(service, name);

            //同一材料名が既に登録されている場合はエラーメッセージを返却
            if (materialmastersCount > 0) {
                return MessageConst.E_MATM_NAME_EXIST.getMessage();
            }

        }

        //エラーが無い場合は空文字を返却
        return "";
    }

    /**
     * @param service MaterialMasterServiceのインスタンス
     * @param name 材料名
     * @return 材料テーブルに登録されている同一名のデータの件数
     */
    private static long isDuplicateMaterialMaster(MaterialMasterService service, String name) {

        long materialMastersCount = service.countByName(name);
        return materialMastersCount;
    }

    /**
     * 単位に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param unit 単位
     * @return エラーメッセージ
     */
    private static String validateUnit(String unit) {

        if (unit == null || unit.equals("")) {
            return MessageConst.E_MATM_NOUNIT.getMessage();
        }

        //入力値がある場合は空文字を返却する
        return "";
    }

}
