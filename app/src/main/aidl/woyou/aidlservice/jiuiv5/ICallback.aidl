package woyou.aidlservice.jiuiv5;

/**
 * 打印服务执行结果的回调(Kết quả thực hiện dịch vụ gọi lại)
 */
interface ICallback {
	/**
	* 返回执行结果(Trả lại kết quả thực thi)
	* @param isSuccess:	  true执行成功，false 执行失败(thành công thật sự, sai không thực hiện được)
	*/
	oneway void onRunResult(boolean isSuccess);
	
	/**
	* 返回结果(字符串数据)(Trả về kết quả (chuỗi dữ liệu))
	* @param result:	结果，打印机上电以来打印长度(单位mm)(Kết quả là chiều dài in (tính bằng mm) kể từ khi máy in được bật)
	*/
	oneway void onReturnString(String result);
	
	/**
	* 执行发生异常(Một ngoại lệ xảy ra trong quá trình thực thi)
	* code：	异常代码(Mã ngoại lệ)
	* msg:	异常描述(Mô tả ngoại lệ)
	*/
	oneway void  onRaiseException(int code, String msg);
}