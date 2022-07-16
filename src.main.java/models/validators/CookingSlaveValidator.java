package models.validators;

import actions.views.CookingSlaveView;
import constants.MessageConst;

/**
 * 料理インスタンスに設定されている値のバリデーションを行うクラス
 */
public class CookingSlaveValidator {

    /**
     * 材料インスタンスについてバリデーションを行う
     * @param csv 料理のインスタンス
     * @return エラー
     */
    public static String validate(CookingSlaveView csv) {

        //量のチェック
        String amountError=validateAmount(csv.getAmount());
        return amountError;
    }

    /**
     * 数値かどうかチェックし、数値では無かったならばエラーメッセージを返却
     * @param 量
     * @return エラーメッセージ
     */
    private static String validateAmount(Integer amount) {
        if(amount <=0) {
            return MessageConst.E_MAT_NOAMOUNT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return null;
    }

}
