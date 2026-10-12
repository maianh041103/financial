package com.study.financial.messages;

public class TransactionMessage {
    public static final String TRANSACTION_TYPE_NOT_NULL = "Loại chi tiêu không được để trống";
    public static final String CATEGORY_NOT_NULL = "Danh mục không được để trống";
    public static final String NAME_NOT_NULL = "Tên khoản chi không được để trống";
    public static final String AMOUNT_NOT_NULL = "Số tiền chi không được để trống";
    public static final String AMOUNT_MIN = "Số tiền phải lớn hơn 0";
    public static final String TRANSACTION_DATE_MAX = "Ngày giao dịch không được lớn hơn ngày hiện tại";
    public static final String TRANSACTION_DATE_NOT_NULL = "Ngày giao dịch không được để trống";
    public static final String CREATE_EXPENSE_SUCCESS = "Thêm mới khoản chi thành công";
    public static final String GET_ALL_SUCCESS = "Lấy danh sách giao dịch thành công";
    public static final String GET_DETAIL_SUCCESS = "Lấy chi tiết giao dịch thành công";
    public static final String TRANSACTION_NOT_EXISTED = "Giao dịch không tồn tại";
    public static final String UPDATE_TRANSACTION_SUCCESS = "Cập nhật giao dịch thành công";
    public static final String DELETE_TRANSACTION_SUCCESS = "Xoá giao dịch thành công";
}
