
# 四、工序 #

## 创建一个新的procedure后,返回自身及links信息 ##

**用例名称**
shouldReturn201WhenCreateProcedureSuccessfully

**URL**
http://localhost:8007/error/500

**请求体**
```
{
	"title":"this is the procedure title."
}
```


**响应体**
```
{
	"timestamp":1477145560138,
	"status":500,
	"error":"Internal Server Error",
	"exception":"java.lang.ClassCastException",
	"message":"Request processing failed; nested exception is java.lang.ClassCastException: com.alibaba.fastjson.JSONObject cannot be cast to com.alibaba.fastjson.JSONArray",
	"path":"/boards/feeId/procedures"
}
```


-------