package models.validators;

import actions.views.CookingView;
import constants.MessageConst;
import services.CookingService;

/**
 * 料理インスタンスに設定されている値のバリデーションを行うクラス
 */
public class CookingValidator {

    /**
     * 料理インスタンスについてバリテーションを行う
     * @param cv 料理インスタンス
     * @return エラー
     */
    public static String validate(CookingService service,CookingView cv,Boolean nameDuplicateCheckFlag) {



        //料理名のチェック
        String nameError=validateName(service,cv.getName(),nameDuplicateCheckFlag);
        return nameError;
    }

    /**
     * 料理名の入力チェックを行い、エラーメッセージを返却
     * @param service CookingServiceのインスタンス
     * @param name 料理名
     * @param nameDuplicateCheckFlag 料理名の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateName(CookingService service,String name,Boolean nameDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if(name == null || name.equals("")) {
            return MessageConst.E_COO_NONAME.getMessage();
        }

        if(nameDuplicateCheckFlag) {
            //料理名の重複チェックを実施

            long cookingsCount=isDuplicateCooking(service,name);

            //同一の料理名が既に登録されている場合はエラーメッセージを返却
            if(cookingsCount > 0) {
                return MessageConst.E_COO_NAME_EXIST.getMessage();
            }
        }

        //エラーが無い場合は空文字を返却
        return "";
    }

    /**
     * @param service CookingServiceのインスタンス
     * @param name 料理名
     * @return 料理テーブルに登録されている同じ料理名のデータの件数
     */
    private static long isDuplicateCooking(CookingService service,String name) {

        long cookingCount=service.countByName(name);
        return cookingCount;
    }



}
