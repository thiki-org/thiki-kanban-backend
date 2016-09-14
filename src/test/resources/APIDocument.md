##### 场景 #####
创建团队时，如果未提供团队名称，则不允许创建
##### 用例名称 #####
creationIsNotAllowedIfTeamNameIsNull
##### URL #####
http://localhost:8007/error/invalidParamsException
##### 请求体 #####
```
{
	
}
```

##### 响应体 #####
```
{
	"timestamp":1473855455157,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"团队名称不可以为空。",
	"path":"/someone/teams",
	"code":400
}
```

-------