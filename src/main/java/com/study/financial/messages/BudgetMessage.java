package com.study.financial.messages;

public class BudgetMessage {
    public static final String CREATE_BUDGET_SUCCESS = "Thêm mới ngân sách chi tiêu thành công";
    public static final String UPDATE_BUDGET_SUCCESS = "Cập nhật ngân sách chi tiêu thành công";
    public static final String GET_ALL_BUDGET_SUCCESS = "Lấy danh sách ngân sách chi tiêu thành công";
    public static final String GET_DETAIL_BUDGET_SUCCESS = "Lấy chi tiết ngân sách chi tiêu thành công";
    public static final String DELETE_BUDGET_SUCCESS = "Xoá ngân sách chi tiêu thành công";
    public static final String BUDGET_NOT_EXISTED = "Ngân sách chi tiêu không tồn tại";
    public static final String BUDGET_EXISTED = "Ngân sách chi tiêu đã tồn tại";
    public static final String BUDGET_NAME_NOT_NULL = "Tên ngân sách không được để trống";
    public static final String BUDGET_AMOUNT_MIN = "Số tiền phải lớn hơn 0";
    public static final String BUDGET_AMOUNT_NOT_NULL = "Số tiền không được để trống";
    public static final String BUDGET_CATEGORY_NOT_NULL = "Danh mục không được để trống";
    public static final String BUDGET_START_DATE_NOT_NULL = "Ngày bắt đầu không được để trống";
    public static final String BUDGET_END_DATE_NOT_NULL = "Ngày kết thục không được để trống";
    public static final String END_DATE_CANNOT_BE_BEFORE_START_DATE = "Ngày kết thúc không được nhỏ hơn ngày bắt đầu";
    public static final String BUDGET_WARNING_PERCENT_NOT_NULL = "Mức độ cảnh báo không được để trống";
    public static final String BUDGET_WARNING_PERCENT_MIN = "Mức độ cảnh báo phải lớn hơn 0";
    public static final String BUDGET_CATEGORY_EXISTED = "Danh mục đã thuộc ngân sách khác";
    public static final String BUDGET_NAME_EXISTED = "Tên danh mục đã tồn tại";
    public static final String BUDGET_CATEGORY_NOT_EXISTED = "Danh mục của ngân sách không tồn tại";
}
