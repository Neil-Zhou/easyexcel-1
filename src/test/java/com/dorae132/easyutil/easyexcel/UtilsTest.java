package com.dorae132.easyutil.easyexcel;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.collect.Lists;

@SuppressWarnings("unchecked")
public class UtilsTest {

	static class TestValue {
		@ExcelCol(title = "姓名")
		private String name;
		@ExcelCol(title = "年龄", order = 1)
		private String age;
		@ExcelCol(title = "学校", order = 3)
		private String school;
		@ExcelCol(title = "年级", order = 2)
		private String clazz;

		public TestValue(String name, String age, String school, String clazz) {
			super();
			this.name = name;
			this.age = age;
			this.school = school;
			this.clazz = clazz;
		}
	}

	private List<TestValue> getData(int count) {
		List<TestValue> dataList = Lists.newArrayListWithCapacity(10000);
		for (int i = 0; i < count; i++) {
			dataList.add(new TestValue("张三" + i, "age: " + i, null, "clazz: " + i));
		}
		return dataList;
	}
	
//	@Test
//	public void testCommonMode() throws Exception {
//		List<TestValue> dataList = getData(100000);
//		long start = System.currentTimeMillis();
//		ExcelProperties<TestValue, File> properties = ExcelProperties.produceCommonProperties("", dataList,
//				"C:\\Users\\Dorae\\Desktop\\ttt\\", "common.xlsx", 0, null, 0, null);
//		File file = (File) ExcelUtils.excelExport(properties, FillSheetModeEnums.COMMON_MODE.getValue());
//		System.out.println("commonMode: " + (System.currentTimeMillis() - start));
//	}
	
	
	@Test
	public void testAppend() throws Exception {
		List<TestValue> dataList = getData(10000);
		long start = System.currentTimeMillis();
		ExcelProperties<TestValue, File> properties = ExcelProperties.produceAppendProperties("",
				"C:\\Users\\Dorae\\Desktop\\ttt\\", "append.xlsx", 0, TestValue.class, 0, null, new AbstractDataSupplier<TestValue>() {
					private int i = 0;

					@Override
					public Pair<List<TestValue>, Boolean> getDatas() {
						boolean hasNext = i < 9;
						i++;
						return Pair.of(dataList, hasNext);
					}
				});
		File file = (File) ExcelUtils.excelExport(properties, FillSheetModeEnums.PARALLEL_APPEND_MODE.getValue());
		System.out.println("apendMode: " + (System.currentTimeMillis() - start));
	}
}
