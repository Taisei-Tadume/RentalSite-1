package jp.ken.jdbc.application.exception;

/**
 * 在庫不足時にスローする例外
 * （主に注文確定処理の安全装置として使用）
 */
public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
