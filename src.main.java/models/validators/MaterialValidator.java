package models.validators;

import actions.views.MaterialView;
import constants.MessageConst;

/**
 * 材料インスタンスに設定されている値のバリデーションを行うクラス
 */
public class MaterialValidator {

   /**
    * 材料インスタンスについてバリテーションを行う
    * @param mv 材料のインスタンス
    * @return エラー
    */
    public static String validate(MaterialView mv){


        //量のチェック
        String amountError= validateAmount(mv.getAmount());
            return amountError;

    }

    /**
     * 量がnullかをチェックし、nullならばエラーメッセージを返却
     * @param amount 量
     * @return エラーメッセージを返却
     *
     */
    public static String validateAmount(Integer amount) {
        if(amount <= 0) {
            return MessageConst.E_MAT_NOAMOUNT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";

    }

    /**
     * 量に入力されたのが数値じゃない場合はエラーメッセージを返却
     * return エラーメッセージを返却
     */
    public static String validateAmount(){
        return MessageConst.E_MAT_NOAMOUNT.getMessage();
    }

}
