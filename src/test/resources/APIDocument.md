
# 十三 、验收标准 #

## 创建验收标准>用户创建完卡片后,可以创建为其创建相应的验收标准 ##

**用例名称**
create_shouldReturn201WhenCreateACSuccessfully

**URL_TEMPLATE**
http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias

**请求体**
```
{
	"summary":"AC-summary",
	"finished":false
}
```


**响应体**
```
{
	"summary":"AC-summary",
	"creationTime":"2016-11-17 11:17:16.936000",
	"_links":{
		"acceptanceCriterias":{
			"href":"http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-17 11:17:16.936000",
	"sortNumber":9999,
	"finished":false,
	"id":"fooId"
}
```


-------