
# 十三 、验收标准 #

## 创建验收标准>用户创建完卡片后,可以创建为其创建相应的验收标准 ##

**用例名称**
create_shouldReturn201WhenCreateACSuccessfully

**URL**
http://localhost:8007/cards/card-fooId/acceptanceCriterias

**请求体**
```
{
	"summary":"AC-summary"
}
```


**响应体**
```
{
	"summary":"AC-summary",
	"creationTime":"2016-10-17 18:27:09.432000",
	"_links":{
		"acceptanceCriterias":{
			"href":"http://localhost:8007/cards/card-fooId/acceptanceCriterias"
		},
		"self":{
			"href":"http://localhost:8007/cards/card-fooId/acceptanceCriterias/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-10-17 18:27:09.432000",
	"id":"fooId",
	"isFinished":0
}
```


-------