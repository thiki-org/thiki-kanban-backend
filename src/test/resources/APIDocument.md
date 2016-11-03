
# 零、入口 #

## 初次访问系统时入口 ##

**用例名称**
enter_shouldReturnEntranceSuccessfully

**URL_TEMPLATE**
http://localhost:8007/entrance

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/entrance",
			"actions":{
				"read":{
					"isAllowed":true
				}
			}
		},
		"publicKey":{
			"href":"http://localhost:8007/publicKey",
			"actions":{
				"read":{
					"isAllowed":true
				}
			}
		},
		"passwordRetrievalApplication":{
			"href":"http://localhost:8007/passwordRetrievalApplication",
			"actions":{}
		}
	},
	"description":"Welcome!"
}
```


-------
# 一、注册 #

## 不允许注册>如果用户名已经存在,则不允许注册 ##

**用例名称**
registerNewUser_shouldRejectWithConflictWhenUserNameExists

**URL_TEMPLATE**
http://localhost:8007/registration

**请求体**
```
{
	"name":"someone",
	"password":"fee",
	"email":"someone@gmail.com"
}
```


**响应体**
```
{
	"timestamp":1478137486288,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"用户名已经被使用,请使用其他用户名。",
	"path":"/registration",
	"code":1001
}
```


-------
## 不允许注册>用户名和邮箱在系统中都不存在,但是密码未通过公钥加密,则不允许注册 ##

**用例名称**
registerNewUser_shouldFailIfPasswordIsNotEncryptedWithPublicKey

**URL_TEMPLATE**
http://localhost:8007/registration

**请求体**
```
{
	"name":"someone",
	"password":"foo",
	"email":"someone@gmail.com"
}
```


**响应体**
```
{
	"timestamp":1478137486396,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"通过私钥解密失败,请确保数据已经通过公钥加密。",
	"path":"/registration",
	"code":400
}
```


-------
## 不允许注册>如果邮箱已经存在,则不允许注册 ##

**用例名称**
registerNewUser_shouldRejectWithConflictWhenUserEmailExists

**URL_TEMPLATE**
http://localhost:8007/registration

**请求体**
```
{
	"name":"someoneElse",
	"password":"fee",
	"email":"someone@gmail.com"
}
```


**响应体**
```
{
	"timestamp":1478137486451,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邮箱已经存在,请使用其他邮箱。",
	"path":"/registration",
	"code":1002
}
```


-------
## 注册成功>根据服务端提供的公钥对密码进行加密,服务端拿到加密的密码后,首先用私钥解密,再通过MD5算法加盐加密 ##

**用例名称**
registerNewUser_shouldReturn201WhenRegisterSuccessfully

**URL_TEMPLATE**
http://localhost:8007/registration

**请求体**
```
{
	"name":"someone",
	"password":"oSMxFNi+i4PeWtctoXWO+IWgi4jbb7a0Ot7s62P1tKv9bndOK3077kv2xoCaORYrToGcN6YaLdi9JYfjRT6836V9xMSkko3nyqHFk2dlHA5BsoGUjGnDRiwva+Ye6/1pXNDctlih/m94xJm1smgMzGyY+h7AwsYxP1Oxjg+RlxE=",
	"email":"someone@gmail.com"
}
```


**响应体**
```
{
	"password":"148412d9df986f739038ad22c77459f2",
	"salt":"fooId",
	"_links":{
		"login":{
			"href":"http://localhost:8007/login",
			"actions":{}
		}
	},
	"name":"someone",
	"id":"fooId",
	"email":"someone@gmail.com"
}
```


-------
# 二、登录 #

## 密码错误>用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配未通过则登录失败 ##

**用例名称**
login_shouldLoginFailedIfUserNameOrPasswordIsIncorrect

**URL_TEMPLATE**
http://localhost:8007/login?identity=someone&password=jFNC2kwuZ5hy3wdobThoINfXgFOHYWBqgYKUVDa8QvkXwB6IY13VJSFKQIdOVJdzINDqPy9VKTfcUZaouAwjakWygYYIB1LgDQzYwwBe2BbD9gJpKwlLRes0j8cesCS%2BSdnwaD4KtZrY%2BGJ%2BwzkddX72znQ2cYfPTFzv9Nl7zbM%3D

**响应体**
```
{
	"timestamp":1478137481341,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"用户名或密码错误。",
	"path":"/login",
	"code":400
}
```


-------
## 登录成功>用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配通过则颁发token ##

**用例名称**
login_loginSuccessfully

**URL_TEMPLATE**
http://localhost:8007/login?identity=someone&password=ATIEvCWlGkQq8p1GQSRQH1T2Pji6OKjpchaSQlFoG8tohmStzNI1zbTEdIIujzH8O1Hg6xvb1oOQiFmLBF1p%2FbMdZ3MrOwru63m41byvabWQgTJTXU8jMsGCj8AQCo3BBxlSl0KlBvSybKJLf68CRYQqyOC5Ag3yUVTb6rdY4gA%3D

**响应体**
```
{
	"_links":{
		"teams":{
			"href":"http://localhost:8007/someone/teams",
			"actions":{}
		},
		"unreadNotificationsTotal":{
			"href":"http://localhost:8007/someone/notifications/unread/total",
			"actions":{}
		},
		"profile":{
			"href":"http://localhost:8007/users/someone/profile",
			"actions":{}
		},
		"boards":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		}
	},
	"userName":"someone",
	"token":"4988ca54a84321490e03fd906b5d2425afba80914c282271fd83ad1438ec8b55976cf77197a77b08c750bfb5e6173790f9f95f4e07a4f273d6fad3645e8377ed8ea865770a8aa4ff05168a98dc2417a4254405fb1639871cfc63f0dd5871a4805dc3778c106d37010b2c20adedd0117a2a8e63632744fa4e33151d880eed022e"
}
```


-------
## 登录信息不完整>用户登录系统时,如果身份信息为空,则不允许登录并告知客户端错误信息 ##

**用例名称**
login_loginFailed

**URL_TEMPLATE**
http://localhost:8007/login?password=foo

**响应体**
```
{
	"timestamp":1478137481460,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"登录失败,请输入您的用户名或邮箱。",
	"path":"/login",
	"code":400
}
```


-------
## 用户不存在>用户登录系统时,如果用户不存在,则不允许登录并告知客户端错误信息 ##

**用例名称**
login_loginFailedIfRegUserIsNotExists

**URL_TEMPLATE**
http://localhost:8007/login?identity=foo&password=foo

**响应体**
```
{
	"timestamp":1478137481547,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"用户不存在,请先注册。",
	"path":"/login",
	"code":2001
}
```


-------
# 三、看板 #

## 看板权限管控>当用户为看板所属团队成员时,但并非团队看板,则只允许读取,不允许其他操作 ##

**用例名称**
allowReadOnlyIfTheUserIsNotTheTeamAndTheBoardOwner

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"owner":"others",
	"creationTime":"2016-11-03 09:44:33.235000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{
				"read":{
					"isAllowed":true
				}
			}
		},
		"team":{
			"href":"http://localhost:8007/teams/teamId-foo",
			"actions":{}
		}
	},
	"author":"others",
	"modificationTime":"2016-11-03 09:44:33.235000",
	"teamId":"teamId-foo",
	"name":"board-name",
	"id":"fooId"
}
```


-------
## 看板权限管控>当用户删除一个指定的看板时,如果该用户并非看板所属团队的成员,且看板非个人所属,则不允许删除 ##

**用例名称**
notAllowedIfCurrentHasNoAuthority

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"timestamp":1478137473408,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.AuthenticationException",
	"message":"操作被阻止!你非当前看板所属团队成员,看板亦非你个人所有。",
	"path":"/someone/boards/fooId",
	"code":3003
}
```


-------
## 成功更新一个board信息 ##

**用例名称**
shouldUpdateSuccessfully

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId

**请求体**
```
{
	"teamId":"teamId-foo",
	"name":"new-name"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:33.428000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{}
		},
		"team":{
			"href":"http://localhost:8007/teams/teamId-foo",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:33.428000",
	"teamId":"teamId-foo",
	"name":"new-name",
	"id":"fooId"
}
```


-------
## 当更新一个board时,如果存在同名,则不允许更新,并告知客户端错误信息 ##

**用例名称**
UpdateIsNotAllowedIfBoardWithSameNameIsAlreadyExists

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId1

**请求体**
```
{
	"name":"board-name2"
}
```


**响应体**
```
{
	"timestamp":1478137473591,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"同名看板已经存在,请使用其它名称。",
	"path":"/someone/boards/fooId1",
	"code":3001
}
```


-------
## 用户根据ID获取board时,如果该board存在,则返回其信息 ##

**用例名称**
shouldReturnBoardWhenBoardIsExist

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"creationTime":"2016-11-03 09:44:33.614000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:33.614000",
	"name":"board-name",
	"id":"fooId"
}
```


-------
## 当用户创建一个board时,如果存在同名,则不允许创建,并告知客户端错误信息 ##

**用例名称**
NotAllowedIfBoardWithSameNameIsAlreadyExists

**URL_TEMPLATE**
http://localhost:8007/someone/boards

**请求体**
```
{
	"name":"board-name"
}
```


**响应体**
```
{
	"timestamp":1478137473697,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"同名看板已经存在,请使用其它名称。",
	"path":"/someone/boards",
	"code":3001
}
```


-------
## 获取指定用户所拥有的boards ##

**用例名称**
findByUserName_shouldReturnAllBoardsSuccessfully

**URL_TEMPLATE**
http://localhost:8007/someone/boards

**响应体**
```
{
	"_links":{
		"worktileTasks":{
			"href":"http://localhost:8007/someone/worktileTasks",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		}
	},
	"boards":[
		{
			"creationTime":"2016-11-03 09:44:33.711000",
			"_links":{
				"all":{
					"href":"http://localhost:8007/someone/boards",
					"actions":{}
				},
				"procedures":{
					"href":"http://localhost:8007/boards/fooId/procedures",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/someone/boards/fooId",
					"actions":{}
				}
			},
			"author":"someone",
			"modificationTime":"2016-11-03 09:44:33.711000",
			"name":"board-name",
			"id":"fooId"
		}
	]
}
```


-------
## 当创建一个board时,如果参数合法,则创建成功并返回创建后的board ##

**用例名称**
shouldReturn201WhenCreateBoardSuccessfully

**URL_TEMPLATE**
http://localhost:8007/someone/boards

**请求体**
```
{
	"name":"board-name"
}
```


**响应体**
```
{
	"owner":"someone",
	"creationTime":"2016-11-03 09:44:33.811000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{
				"modify":{
					"isAllowed":true
				},
				"read":{
					"isAllowed":true
				},
				"delete":{
					"isAllowed":true
				}
			}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:33.811000",
	"name":"board-name",
	"id":"fooId"
}
```


-------
## 当用户删除一个指定的board时,如果该board存在,则删除成功 ##

**用例名称**
shouldDeleteSuccessfullyWhenTheBoardIsExist

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		}
	}
}
```


-------
## 当看板不存在时,则不允许更新 ##

**用例名称**
shouldUpdateFailedWhenTheBoardIsNotExist

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId

**请求体**
```
{
	"name":"new-name"
}
```


**响应体**
```
{
	"timestamp":1478137473918,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"看板不存在。",
	"path":"/someone/boards/fooId",
	"code":3002
}
```


-------
## 当用户删除一个指定的board时,如果该board不存在,则返回客户端404错误 ##

**用例名称**
shouldThrowResourceNotFoundExceptionWhenBoardToDeleteIsNotExist

**URL_TEMPLATE**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"timestamp":1478137473947,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"看板不存在。",
	"path":"/someone/boards/fooId",
	"code":3002
}
```


-------
# 四、工序 #

## 更新procedure时,如果参数合法且待更新的procedure存在,则更新成功 ##

**用例名称**
shouldUpdateSuccessfully

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures/fooId

**请求体**
```
{
	"title":"newTitle"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:45.514000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures",
			"actions":{}
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId",
			"actions":{}
		}
	},
	"author":"1",
	"modificationTime":"2016-11-03 09:44:45.514000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"newTitle"
}
```


-------
## 当根据procedureId查找procedure时,如果procedure存在,则将其返回 ##

**用例名称**
shouldReturnProcedureWhenFindProcedureById

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures/fooId

**响应体**
```
{
	"creationTime":"2016-11-03 09:44:45.585000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures",
			"actions":{}
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId",
			"actions":{}
		}
	},
	"author":"1",
	"modificationTime":"2016-11-03 09:44:45.585000",
	"sortNumber":0,
	"boardId":"feeId",
	"id":"fooId",
	"title":"this is the first procedure."
}
```


-------
## 创建新的procedure时,如果名称为空,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldFailedIfProcedureTitleIsEmpty

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"title":""
}
```


**响应体**
```
{
	"timestamp":1478137485682,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"工序名称不能为空。",
	"path":"/boards/feeId/procedures",
	"code":400
}
```


-------
## 更新procedure时,如果参数合法但待更新的procedure不存在,则更新失败 ##

**用例名称**
update_shouldFailedWhenTheProcedureToUpdateIsNotExists

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures/fooId

**请求体**
```
{
	"title":"newTitle"
}
```


**响应体**
```
{
	"timestamp":1478137485737,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"工序不存在",
	"path":"/boards/feeId/procedures/fooId",
	"code":4003
}
```


-------
## 通过boardId获取所有的procedure ##

**用例名称**
shouldReturnAllEntriesSuccessfully

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures",
			"actions":{}
		},
		"sortNumbers":{
			"href":"http://localhost:8007/boards/feeId/procedures/sortNumbers",
			"actions":{}
		}
	},
	"procedures":[
		{
			"creationTime":"2016-11-03 09:44:45.762000",
			"_links":{
				"all":{
					"href":"http://localhost:8007/boards/feeId/procedures",
					"actions":{}
				},
				"cards":{
					"href":"http://localhost:8007/procedures/fooId/cards",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/boards/feeId/procedures/fooId",
					"actions":{}
				}
			},
			"author":"tao",
			"modificationTime":"2016-11-03 09:44:45.762000",
			"sortNumber":0,
			"boardId":"feeId",
			"id":"fooId",
			"title":"this is the first procedure."
		}
	]
}
```


-------
## 重新排序>用户创建工序后,可以调整其顺序 ##

**用例名称**
resortProcedure

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures/sortNumbers

**请求体**
```
[
	{
		"sortNumber":2,
		"id":"procedure-fooId1"
	},
	{
		"sortNumber":3,
		"id":"procedure-fooId2"
	}
]
```


**响应体**
```
{
	"_links":{
		"procedures":{
			"href":"http://localhost:8007/boards/feeId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/sortNumbers",
			"actions":{}
		}
	},
	"procedures":[
		{
			"creationTime":"2016-11-03 09:44:45.827000",
			"_links":{
				"all":{
					"href":"http://localhost:8007/boards/feeId/procedures",
					"actions":{}
				},
				"cards":{
					"href":"http://localhost:8007/procedures/procedure-fooId1/cards",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/boards/feeId/procedures/procedure-fooId1",
					"actions":{}
				}
			},
			"author":"1",
			"modificationTime":"2016-11-03 09:44:45.827000",
			"sortNumber":2,
			"boardId":"feeId",
			"id":"procedure-fooId1",
			"title":"procedureTitle1"
		},
		{
			"creationTime":"2016-11-03 09:44:45.828000",
			"_links":{
				"all":{
					"href":"http://localhost:8007/boards/feeId/procedures",
					"actions":{}
				},
				"cards":{
					"href":"http://localhost:8007/procedures/procedure-fooId2/cards",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/boards/feeId/procedures/procedure-fooId2",
					"actions":{}
				}
			},
			"author":"1",
			"modificationTime":"2016-11-03 09:44:45.828000",
			"sortNumber":3,
			"boardId":"feeId",
			"id":"procedure-fooId2",
			"title":"procedureTitle2"
		}
	]
}
```


-------
## 创建新的procedure时,如果名称长度超限,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsTooLong

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"title":"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限"
}
```


**响应体**
```
{
	"timestamp":1478137485930,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"工序名称长度超限,请保持在30个字符以内。",
	"path":"/boards/feeId/procedures",
	"code":400
}
```


-------
## 当删除一个procedure时,如果待删除的procedure存在,则删除成功 ##

**用例名称**
shouldDeleteSuccessfullyWhenTheProcedureIsExist

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures/fooId

**响应体**
```
{
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures",
			"actions":{}
		}
	}
}
```


-------
## 创建新的procedure时,如果名称为空字符串,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsEmpty

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"title":""
}
```


**响应体**
```
{
	"timestamp":1478137486002,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"工序名称不能为空。",
	"path":"/boards/feeId/procedures",
	"code":400
}
```


-------
## 当删除一个procedure时,如果待删除的procedure不存在,则删除成功并返回客户端错误 ##

**用例名称**
shouldThrowResourceNotFoundExceptionWhenProcedureToDeleteIsNotExist

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures/fooId

**响应体**
```
{
	"timestamp":1478137486051,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"工序不存在",
	"path":"/boards/feeId/procedures/fooId",
	"code":4003
}
```


-------
## 创建新的procedure时,同一看板下已经存在同名,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsAlreadyExits

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"title":"procedure"
}
```


**响应体**
```
{
	"timestamp":1478137486109,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"该名称已经被使用,请使用其它名称。",
	"path":"/boards/feeId/procedures",
	"code":4001
}
```


-------
## 创建一个新的procedure后,返回自身及links信息 ##

**用例名称**
shouldReturn201WhenCreateProcedureSuccessfully

**URL_TEMPLATE**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"title":"this is the procedure title."
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:46.150000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures",
			"actions":{}
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId",
			"actions":{}
		}
	},
	"author":"fooName",
	"modificationTime":"2016-11-03 09:44:46.150000",
	"sortNumber":0,
	"boardId":"feeId",
	"id":"fooId",
	"title":"this is the procedure title."
}
```


-------
# 五、卡片 #

## 当更新一个卡片时,如果待更新的卡片不存在,则抛出资源不存在的错误 ##

**用例名称**
update_shouldThrowResourceNotFoundExceptionWhenCardToUpdateIsNotExist

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1478137473995,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"卡片未找到。",
	"path":"/procedures/1/cards/fooId",
	"code":5001
}
```


-------
## 当创建一个卡片时,如果卡片概述长度超过50,则创建失败 ##

**用例名称**
create_shouldFailedIfSummaryIsTooLong

**URL_TEMPLATE**
http://localhost:8007/procedures/fooId/cards

**请求体**
```
{
	"summary":"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限"
}
```


**响应体**
```
{
	"timestamp":1478137474033,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"卡片概述长度超限,请保持在50个字符以内。",
	"path":"/procedures/fooId/cards",
	"code":400
}
```


-------
## 根据ID查找一个卡片时,如果卡片存在,则返回该卡片 ##

**用例名称**
findById_shouldReturnCardSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/1

**响应体**
```
{
	"summary":"this is the card summary.",
	"creationTime":"2016-11-03 09:44:34.049000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/1/assignments",
			"actions":{}
		},
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/1/cards/1/acceptanceCriterias",
			"actions":{}
		},
		"comments":{
			"href":"http://localhost:8007/procedures/1/cards/1/comments",
			"actions":{}
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/1",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:34.049000",
	"sortNumber":0,
	"id":"1",
	"procedureId":"1",
	"content":"play badminton"
}
```


-------
## 当一个卡片从某个procedure移动到另一个procedure时,不仅需要重新排序目标procedure,也要对原始procedure排序 ##

**用例名称**
update_shouldResortSuccessfullyWhenCardIsFromAntherProcedure

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId6

**请求体**
```
{
	"summary":"newSummary",
	"sortNumber":3,
	"procedureId":"1"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"creationTime":"2016-11-03 09:44:34.124000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId6/assignments",
			"actions":{}
		},
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/1/cards/fooId6/acceptanceCriterias",
			"actions":{}
		},
		"comments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId6/comments",
			"actions":{}
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId6",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:34.124000",
	"sortNumber":3,
	"id":"fooId6",
	"procedureId":"1"
}
```


-------
## 根据ID查找一个卡片时,如果卡片不存在,则抛出400错误 ##

**用例名称**
update_shouldFailedWhenCardIsNotExist

**URL_TEMPLATE**
http://localhost:8007/procedures/fooId/cards/feeId

**响应体**
```
{
	"timestamp":1478137474199,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"卡片未找到。",
	"path":"/procedures/fooId/cards/feeId",
	"code":5001
}
```


-------
## 更新卡片成功 ##

**用例名称**
update_shouldReturn200WhenUpdateCardSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId

**请求体**
```
{
	"summary":"newSummary",
	"sortNumber":3,
	"procedureId":"1"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"creationTime":"2016-11-03 09:44:34.232000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments",
			"actions":{}
		},
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/acceptanceCriterias",
			"actions":{}
		},
		"comments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/comments",
			"actions":{}
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:34.232000",
	"sortNumber":3,
	"id":"fooId",
	"procedureId":"1"
}
```


-------
## 当删除一个卡片时,如果待删除的卡片不存在,400 ##

**用例名称**
delete_shouldDeleteFailedWhenTheCardIsNotExist

**URL_TEMPLATE**
http://localhost:8007/procedures/feeId/cards/non-exists-cardId

**响应体**
```
{
	"timestamp":1478137474313,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"卡片未找到。",
	"path":"/procedures/feeId/cards/non-exists-cardId",
	"code":5001
}
```


-------
## 当根据procedureId查找其下属的卡片时,可以返回其所有卡片 ##

**用例名称**
shouldReturnCardsWhenFindCardsByProcedureIdSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/fooId/cards

**响应体**
```
{
	"cards":[
		{
			"summary":"this is the card summary.",
			"creationTime":"2016-11-03 09:44:34.332000",
			"_links":{
				"assignments":{
					"href":"http://localhost:8007/procedures/fooId/cards/card-fooId/assignments",
					"actions":{}
				},
				"acceptanceCriterias":{
					"href":"http://localhost:8007/procedures/fooId/cards/card-fooId/acceptanceCriterias",
					"actions":{}
				},
				"comments":{
					"href":"http://localhost:8007/procedures/fooId/cards/card-fooId/comments",
					"actions":{}
				},
				"cards":{
					"href":"http://localhost:8007/procedures/fooId/cards",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/procedures/fooId/cards/card-fooId",
					"actions":{}
				}
			},
			"author":"someone",
			"modificationTime":"2016-11-03 09:44:34.332000",
			"sortNumber":0,
			"id":"card-fooId",
			"procedureId":"fooId",
			"content":"play badminton"
		}
	],
	"_links":{
		"self":{
			"href":"http://localhost:8007/procedures/fooId/cards",
			"actions":{}
		},
		"sortNumbers":{
			"href":"http://localhost:8007/procedures/fooId/cards/sortNumbers",
			"actions":{}
		}
	}
}
```


-------
## 当创建一个卡片时,如果卡片所属的procedure并不存在,则创建失败 ##

**用例名称**
create_shouldCreateFailedWhenProcedureIsNotFound

**URL_TEMPLATE**
http://localhost:8007/procedures/non-exists-procedureId/cards

**请求体**
```
{
	"summary":"summary"
}
```


**响应体**
```
{
	"timestamp":1478137474428,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[non-exists-procedureId] is not found.",
	"path":"/procedures/non-exists-procedureId/cards",
	"code":404
}
```


-------
## 当删除一个卡片时,如果卡片存在,则删除成功 ##

**用例名称**
delete_shouldDeleteSuccessfullyWhenTheCardIsExist

**URL_TEMPLATE**
http://localhost:8007/procedures/feeId/cards/fooId

**响应体**
```
{
	"_links":{
		"cards":{
			"href":"http://localhost:8007/procedures/feeId/cards",
			"actions":{}
		}
	}
}
```


-------
## 创建一个新的卡片 ##

**用例名称**
create_shouldReturn201WhenCreateCardSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/fooId/cards

**请求体**
```
{
	"summary":"summary"
}
```


**响应体**
```
{
	"summary":"summary",
	"creationTime":"2016-11-03 09:44:34.522000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/fooId/cards/fooId/assignments",
			"actions":{}
		},
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/fooId/cards/fooId/acceptanceCriterias",
			"actions":{}
		},
		"comments":{
			"href":"http://localhost:8007/procedures/fooId/cards/fooId/comments",
			"actions":{}
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/fooId/cards/fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:34.522000",
	"sortNumber":0,
	"id":"fooId",
	"procedureId":"fooId"
}
```


-------
## 当创建一个卡片时,如果卡片概述为空,则创建失败 ##

**用例名称**
create_shouldFailedIfSummaryIsNull

**URL_TEMPLATE**
http://localhost:8007/procedures/fooId/cards

**请求体**
```
{
	"summary":""
}
```


**响应体**
```
{
	"timestamp":1478137474617,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"卡片概述不能为空。",
	"path":"/procedures/fooId/cards",
	"code":400
}
```


-------
## 当根据procedureID查找卡片时,如果procedure不存在,则抛出404异常 ##

**用例名称**
findCardsByProcedureId_shouldReturn404WhenProcedureIsNotFound

**URL_TEMPLATE**
http://localhost:8007/procedures/2/cards

**响应体**
```
{
	"timestamp":1478137474652,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[2] is not found.",
	"path":"/procedures/2/cards",
	"code":404
}
```


-------
# 六、团队 #

## 获取指定团队信息>用户根据ID获取team时,如果该team不存在,则告知客户端错误 ##

**用例名称**
shouldNoticeClientIfTeamIsNotExist

**URL_TEMPLATE**
http://localhost:8007/teams/fooId

**响应体**
```
{
	"timestamp":1478137493740,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/fooId",
	"code":6002
}
```


-------
## 创建团队时，如果团队名称为空，则不允许创建 ##

**用例名称**
creationIsNotAllowedIfTeamNameIsEmpty

**URL_TEMPLATE**
http://localhost:8007/someone/teams

**请求体**
```
{
	"name":""
}
```


**响应体**
```
{
	"timestamp":1478137493789,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"团队名称不可以为空。",
	"path":"/someone/teams",
	"code":400
}
```


-------
## 更新团队信息>当团队不存在时,不允许更新 ##

**用例名称**
notAllowedIfTeamIsNotExist

**URL_TEMPLATE**
http://localhost:8007/teams/teamId-foo

**请求体**
```
{
	"name":"new-name"
}
```


**响应体**
```
{
	"timestamp":1478137493838,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/teamId-foo",
	"code":6002
}
```


-------
## 用户根据ID获取team时,如果该team存在,则返回其信息 ##

**用例名称**
shouldReturnBoardWhenTeamIsExist

**URL_TEMPLATE**
http://localhost:8007/teams/fooId

**响应体**
```
{
	"creationTime":"2016-11-03 09:44:53.887000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:53.887000",
	"name":"team-name",
	"id":"fooId"
}
```


-------
## 创建团队时，如果团队名称超限，则不允许创建 ##

**用例名称**
creationIsNotAllowedIfTeamNameIsTooLong

**URL_TEMPLATE**
http://localhost:8007/someone/teams

**请求体**
```
{
	"name":"团队名称团队名称团队名称团队名称团队名称团队名称团队名称"
}
```


**响应体**
```
{
	"timestamp":1478137494058,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"看板名称过长,请保持在10个字以内。",
	"path":"/someone/teams",
	"code":400
}
```


-------
## 根据用户名获取其所在团队 ##

**用例名称**
loadTheTeamsWhichTheUserIsIn

**URL_TEMPLATE**
http://localhost:8007/someone/teams

**响应体**
```
[
	{
		"creationTime":"2016-11-03 09:44:54.096000",
		"_links":{
			"members":{
				"href":"http://localhost:8007/teams/fooId/members"
			},
			"self":{
				"href":"http://localhost:8007/teams/fooId"
			}
		},
		"author":"someone",
		"modificationTime":"2016-11-03 09:44:54.096000",
		"name":"team-name",
		"id":"fooId"
	}
]
```


-------
## 更新团队信息>用户创建一个团队后,可以更新该团队的信息 ##

**用例名称**
updateTeam

**URL_TEMPLATE**
http://localhost:8007/teams/teamId-foo

**请求体**
```
{
	"name":"new-name"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:54.198000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/teamId-foo/members",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/teams/teamId-foo",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:54.198000",
	"name":"new-name",
	"id":"teamId-foo"
}
```


-------
## 创建一个团队 ##

**用例名称**
create_shouldReturn201WhenCreateTeamSuccessfully

**URL_TEMPLATE**
http://localhost:8007/someone/teams

**请求体**
```
{
	"name":"思奇团队讨论组"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:54.308000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:54.308000",
	"name":"思奇团队讨论组",
	"id":"fooId"
}
```


-------
## 创建团队时，如果在本人名下已经存在相同名称的团队，则不允许创建 ##

**用例名称**
creationIsNotAllowedIfTeamNameIsConflict

**URL_TEMPLATE**
http://localhost:8007/someone/teams

**请求体**
```
{
	"name":"team-name"
}
```


**响应体**
```
{
	"timestamp":1478137494396,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"同名团队已经存在。",
	"path":"/someone/teams",
	"code":6001
}
```


-------
## 创建团队时，如果未提供团队名称，则不允许创建 ##

**用例名称**
creationIsNotAllowedIfTeamNameIsNull

**URL_TEMPLATE**
http://localhost:8007/someone/teams

**请求体**
```
{
}
```


**响应体**
```
{
	"timestamp":1478137494449,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"团队名称不可以为空。",
	"path":"/someone/teams",
	"code":400
}
```


-------
# 七、团队邀请 #

## 如果邀请人为空,怎不允许发送邀请 ##

**用例名称**
NotAllowedIfInviteeIsEmpty

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":""
}
```


**响应体**
```
{
	"timestamp":1478137486894,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"请指定被邀请的成员。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":400
}
```


-------
## 接受邀请>用户接受邀请时,若邀请不存在,则告知用户相关错误 ##

**用例名称**
throwExceptionIfInvitationIsNotExistWhenAcceptingInvitation

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"timestamp":1478137486938,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请函不存在或已失效。",
	"path":"/teams/foo-team-Id/members/invitation/invitation-id",
	"code":7005
}
```


-------
## 用户可以通过用户名邀请其他成员加入到团队中 ##

**用例名称**
inviteOthersWithEmailToJoinTeam

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"thiki2016@163.com"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:47.013000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId",
			"actions":{}
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:47.013000",
	"isAccepted":false,
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
## 查看邀请>用户查看邀请时,若邀请不存在,则告知用户相关错误 ##

**用例名称**
throwExceptionIfInvitationIsNotExistWhenLoadingInvitation

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"timestamp":1478137488293,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请函不存在或已失效。",
	"path":"/teams/foo-team-Id/members/invitation/invitation-id",
	"code":7005
}
```


-------
## 如果被邀请人已经是团队的成员,则不允许发送邀请 ##

**用例名称**
NotAllowedIfInviteeIsAlreadyAMemberOfTheTeam

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1478137488368,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请对象已经是该团队成员,无须再次邀请。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":7004
}
```


-------
## 接受邀请>用户接受邀请时,若之前已经接受,则告知用户相关错误 ##

**用例名称**
throwExceptionIfInvitationIsAlreadyAccepted

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"timestamp":1478137488424,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"您此前已经接受邀请。",
	"path":"/teams/foo-team-Id/members/invitation/invitation-id",
	"code":7006
}
```


-------
## 如果此前已经存在相同的邀请,则取消之前的邀请 ##

**用例名称**
cancelPreviousInvitationBeforeSendingNewInvitation

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:48.568000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId",
			"actions":{}
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:48.568000",
	"isAccepted":false,
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
## 查看邀请>当用户接收到邀请后,可以查看邀请的具体内容 ##

**用例名称**
loadingInvitationDetailAfterAcceptingInvitation

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"creationTime":"2016-11-03 09:44:49.969000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id",
			"actions":{}
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:49.969000",
	"isAccepted":false,
	"teamId":"foo-team-id",
	"inviter":"someone",
	"id":"invitation-id",
	"invitee":"invitee-user"
}
```


-------
## 接受邀请>用户接受邀请,并成功成为团队的一员 ##

**用例名称**
AcceptInvitation

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-id/members/invitation/invitation-id

**响应体**
```
{
	"creationTime":"2016-11-03 09:44:50.053000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-id/members/invitation/invitation-id",
			"actions":{}
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-id",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:50.053000",
	"isAccepted":true,
	"teamId":"foo-team-id",
	"inviter":"inviter",
	"id":"invitation-id",
	"invitee":"someone"
}
```


-------
## 用户可以通过用户名邀请其他成员加入到团队中 ##

**用例名称**
inviteOthersWithUserNameToJoinTeam

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:50.236000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId",
			"actions":{}
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:50.236000",
	"isAccepted":false,
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
## 如果被邀请人不存在,则不允许发送邀请 ##

**用例名称**
NotAllowedIfInviteeIsNotExist

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1478137491967,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"被邀请的成员不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":7002
}
```


-------
## 接受邀请>用户接受邀请时,若已经是团队一员,则告知用户相关错误 ##

**用例名称**
throwExceptionIfInviteeIsAlreadyAMember

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"timestamp":1478137491998,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"您此前已经接受邀请。",
	"path":"/teams/foo-team-Id/members/invitation/invitation-id",
	"code":7006
}
```


-------
## 如果邀请人并非团队的成员则不允许发送邀请 ##

**用例名称**
NotAllowedIfInviterIsNotAMemberOfTheTeam

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1478137492070,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请人并不是当前团队的成员,不允许邀请他人进入该团队。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":7003
}
```


-------
## 邀请发出后,在消息中心通知用户 ##

**用例名称**
addNotificationAfterSendingInvitation

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:52.154000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId",
			"actions":{}
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id",
			"actions":{}
		}
	},
	"modificationTime":"2016-11-03 09:44:52.154000",
	"isAccepted":false,
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
## 如果邀请加入的团队并不存在,则不允许发送邀请 ##

**用例名称**
NotAllowedIfTeamIsNotExist

**URL_TEMPLATE**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1478137493700,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":6002
}
```


-------
# 八、团队成员 #

## 加入团队时,如果待加入的成员已经在团队中,则不允许加入 ##

**用例名称**
joinTeam_shouldReturnFailedIfMemberIsAlreadyIn

**URL_TEMPLATE**
http://localhost:8007/teams/foo-teamId/teamMembers

**请求体**
```
{
	"member":"someone"
}
```


**响应体**
```
{
	"timestamp":1478137486574,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"Member named someone is already in the teams.",
	"path":"/teams/foo-teamId/teamMembers",
	"code":400
}
```


-------
## 当用户加入一个团队后，可以获取该团队的所有成员 ##

**用例名称**
loadTeamMembersByTeamId

**URL_TEMPLATE**
http://localhost:8007/teams/foo-teamId/members

**响应体**
```
{
	"_links":{
		"invitation":{
			"href":"http://localhost:8007/teams/foo-teamId/members/invitation",
			"actions":{}
		}
	},
	"members":[
		{
			"_links":{
				"profile":{
					"href":"http://localhost:8007/users/someone/profile",
					"actions":{}
				},
				"avatar":{
					"href":"http://localhost:8007/users/someone/avatar",
					"actions":{}
				}
			},
			"userName":"someone",
			"email":"someone@gmail.com"
		}
	]
}
```


-------
## 加入团队时,如果该团队并不存在,则不允许加入 ##

**用例名称**
joinTeam_shouldReturnFailedIfTeamIsNotExist

**URL_TEMPLATE**
http://localhost:8007/teams/foo-teamId/teamMembers

**请求体**
```
{
	"member":"someone"
}
```


**响应体**
```
{
	"timestamp":1478137486691,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/foo-teamId/teamMembers",
	"code":6002
}
```


-------
## 若当前用户并非团队成员，则不允许获取 ##

**用例名称**
NotAllowedIfCurrentUserIsNotAMemberOfTheTeamWhenLoadingTeamMembersByTeamId

**URL_TEMPLATE**
http://localhost:8007/teams/foo-teamId/members

**响应体**
```
{
	"timestamp":1478137486726,
	"status":401,
	"error":"Unauthorized",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"当前用户非该团队成员。",
	"path":"/teams/foo-teamId/members",
	"code":8001
}
```


-------
## 加入一个团队 ##

**用例名称**
joinTeam_shouldReturn201WhenJoinTeamSuccessfully

**URL_TEMPLATE**
http://localhost:8007/teams/foo-teamId/teamMembers

**请求体**
```
{
	"member":"someone"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:46.771000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-teamId/teamMembers",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:46.771000",
	"teamId":"foo-teamId",
	"member":"someone",
	"id":"fooId"
}
```


-------
## 当用户加入一个团队后，可以获取该团队的所有成员。但是当团队不存在时,则不允许获取。 ##

**用例名称**
NotAllowedIfTeamIsNotExitsWhenLoadingTeamMembersByTeamId

**URL_TEMPLATE**
http://localhost:8007/teams/foo-teamId/members

**响应体**
```
{
	"timestamp":1478137486837,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/foo-teamId/members",
	"code":6002
}
```


-------
# 九、密码 #

## 用户重置密码后，若再次重置，告知客户端请求无效 ##

**用例名称**
ResetPasswordIsNotAllowedIfTheApplicationHasBeenAlreadyReset

**URL_TEMPLATE**
http://localhost:8007/tao/password

**请求体**
```
{
	"password":"foo"
}
```


**响应体**
```
{
	"timestamp":1478137481956,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"未找到密码重置申请记录。",
	"path":"/tao/password",
	"code":9004
}
```


-------
## 验证码使用后若再次被使用，告示客户端验证码无效 ##

**用例名称**
verificationCodeWillBeInvalidIfAlreadyBeingUsed

**URL_TEMPLATE**
http://localhost:8007/tao/passwordResetApplication

**请求体**
```
{
	"verificationCode":"000000"
}
```


**响应体**
```
{
	"timestamp":1478137482022,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"未找到密码找回申请记录,请核对你的邮箱或重新发送验证码。",
	"path":"/tao/passwordResetApplication",
	"code":9003
}
```


-------
## 用户取得验证码后，和邮箱一起发送到服务端验证，如果验证码正确且未过期，则发送密码重置的链接 ##

**用例名称**
verifyVerificationCode

**URL_TEMPLATE**
http://localhost:8007/tao/passwordResetApplication

**请求体**
```
{
	"verificationCode":"000000"
}
```


**响应体**
```
{
	"_links":{
		"password":{
			"href":"http://localhost:8007/tao/password",
			"actions":{}
		}
	}
}
```


-------
## 当用户请求找回密码时,需要提供邮箱,如果未提供则告知客户端错误 ##

**用例名称**
NotAllowedIfEmailIsNotProvide

**URL_TEMPLATE**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
}
```


**响应体**
```
{
	"timestamp":1478137482188,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"用于找回密码的邮箱不能为空.",
	"path":"/passwordRetrievalApplication",
	"code":400
}
```


-------
## 用户通过验证码验证,重置密码成功。 ##

**用例名称**
resetPassword

**URL_TEMPLATE**
http://localhost:8007/tao/password

**请求体**
```
{
	"password":"BPPPY4tS1JGTTRIgF7yWgYLW1J63NkZNo0Zsuz4Noxmae2BG1qNTbtLxUwoa7NPnJfZREbAiKnkVI+Wu5y3PMwWBaTi6K16gsFclhbsFst9MyVmU8AbPe2lMUaLLrTaCR/tYcAJgtUQbH6FJ6vB+dprOzviDvxsyTj3UzWz461o="
}
```


**响应体**
```
{
	"_links":{
		"login":{
			"href":"http://localhost:8007/login",
			"actions":{}
		}
	}
}
```


-------
## 邮箱通过格式校验且存在后，创建密码找回申请记前,如果存在未完成的申请,则将其废弃 ##

**用例名称**
discardingUnfinishedPasswordRetrievalApplication

**URL_TEMPLATE**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
	"email":"766191920@qq.com"
}
```


**响应体**
```
{
	"_links":{
		"passwordResetApplication":{
			"href":"http://localhost:8007/tao/passwordResetApplication",
			"actions":{}
		}
	}
}
```


-------
## 验证码超过五分钟后,验证失败 ##

**用例名称**
verificationCodeTimeOut

**URL_TEMPLATE**
http://localhost:8007/tao/passwordResetApplication

**请求体**
```
{
	"verificationCode":"000000"
}
```


**响应体**
```
{
	"timestamp":1478137483851,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"验证码已过期。",
	"path":"/tao/passwordResetApplication",
	"code":9002
}
```


-------
## 邮箱通过格式校验且存在后，发送找回密码的验证码到邮箱 ##

**用例名称**
sendVerificationCode

**URL_TEMPLATE**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
	"email":"766191920@qq.com"
}
```


**响应体**
```
{
	"_links":{
		"passwordResetApplication":{
			"href":"http://localhost:8007/tao/passwordResetApplication",
			"actions":{}
		}
	}
}
```


-------
## 当用户请求找回密码时,需要提供邮箱,如果邮箱不存在则告知客户端错误 ##

**用例名称**
NotAllowedIfEmailFormatIsNotExists

**URL_TEMPLATE**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
	"email":"email@email.com"
}
```


**响应体**
```
{
	"timestamp":1478137485404,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邮箱不存在。",
	"path":"/passwordRetrievalApplication",
	"code":9001
}
```


-------
## 当用户请求找回密码时,需要提供邮箱,如果邮箱格式错误则告知客户端错误 ##

**用例名称**
NotAllowedIfEmailFormatIsNotCorrect

**URL_TEMPLATE**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
	"email":"email"
}
```


**响应体**
```
{
	"timestamp":1478137485447,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"邮箱格式错误.",
	"path":"/passwordRetrievalApplication",
	"code":400
}
```


-------
## 验证码错误,验证失败 ##

**用例名称**
VerificationWillBeFailedIfVerificationCodeIsNotCorrect

**URL_TEMPLATE**
http://localhost:8007/tao/passwordResetApplication

**请求体**
```
{
	"verificationCode":"000001"
}
```


**响应体**
```
{
	"timestamp":1478137485488,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"验证码错误。",
	"path":"/tao/passwordResetApplication",
	"code":9005
}
```


-------
# 十 、消息 #

## 获取所有消息>用户登录后,可以在消息中心查看所有消息,以便及时处理未读消息或重新查看已读消息 ##

**用例名称**
loadAllNotifications

**URL_TEMPLATE**
http://localhost:8007/someone/notifications

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications",
			"actions":{}
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications",
			"actions":{}
		}
	},
	"notifications":[
		{
			"displayTime":"刚刚",
			"creationTime":"2016-11-03 09:44:41.569000",
			"receiver":"someone",
			"sender":"sender@gmail.com",
			"_links":{
				"self":{
					"href":"http://localhost:8007/someone/notifications/foo-notification-id",
					"actions":{}
				},
				"notifications":{
					"href":"http://localhost:8007/someone/notifications",
					"actions":{}
				}
			},
			"isRead":false,
			"link":"http://hello.com",
			"typeName":"团队邀请",
			"id":"foo-notification-id",
			"type":"team-members-invitation",
			"content":"content"
		}
	]
}
```


-------
## 获取指定消息>用户可以在消息中心查看某条具体的消息,查看完毕后将该条消息设置为已读 ##

**用例名称**
loadNotificationByID

**URL_TEMPLATE**
http://localhost:8007/someone/notifications/foo-notification-id

**响应体**
```
{
	"displayTime":"刚刚",
	"creationTime":"2016-11-03 09:44:41.680000",
	"receiver":"someone",
	"sender":"sender@gmail.com",
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications/foo-notification-id",
			"actions":{}
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications",
			"actions":{}
		}
	},
	"isRead":true,
	"link":"http://hello.com",
	"typeName":"notificationType",
	"id":"foo-notification-id",
	"type":"notificationType",
	"content":"content"
}
```


-------
## 获取未读消息数>用户登录后,可以获取未读消息数量,以便在醒目位置显示提醒用户及时处理 ##

**用例名称**
loadUnreadNotificationAfterLoading

**URL_TEMPLATE**
http://localhost:8007/someone/notifications/unread/total

**响应体**
```
{
	"unreadNotificationsTotal":1,
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications/unread/total",
			"actions":{}
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications",
			"actions":{}
		}
	}
}
```


-------
## 获取指定消息>查看某条具体的消息时，如果该消息已读，则加载后不必再设置为已读 ##

**用例名称**
notSetNotificationReadIfItHasAlreadyBeenReadAfterLoading

**URL_TEMPLATE**
http://localhost:8007/someone/notifications/foo-notification-id

**响应体**
```
{
	"displayTime":"刚刚",
	"creationTime":"2016-11-03 09:44:41.793000",
	"receiver":"someone",
	"sender":"sender@gmail.com",
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications/foo-notification-id",
			"actions":{}
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications",
			"actions":{}
		}
	},
	"isRead":true,
	"link":"http://hello.com",
	"typeName":"notificationType",
	"id":"foo-notification-id",
	"type":"notificationType",
	"content":"content"
}
```


-------
# 十一 、任务认领 #

## 当用户根据cardID获取分配记录时,如果指定的卡片并不存在,则返回404客户端错误 ##

**用例名称**
findByCardId_shouldReturnErrorWhenCardIsNotExist

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/cardId-foo/assignments

**响应体**
```
{
	"timestamp":1478137472864,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"卡片未找到。",
	"path":"/procedures/1/cards/cardId-foo/assignments",
	"code":5001
}
```


-------
## 当用户根据cardID获取分配记录时,如果指定的卡片存在,则返回分配记录集合 ##

**用例名称**
findByCardId_shouldReturnAssignmentsSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/cardId-foo/assignments

**响应体**
```
[
	{
		"_links":{
			"all":{
				"href":"http://localhost:8007/boards/1/procedures"
			},
			"assignments":{
				"href":"http://localhost:8007/procedures/1/cards/cardId-foo/assignments"
			},
			"self":{
				"href":"http://localhost:8007/procedures/1/cards/cardId-foo/assignments/fooId"
			},
			"card":{
				"href":"http://localhost:8007/procedures/1/cards/cardId-foo"
			}
		},
		"author":"authorId-foo",
		"cardId":"cardId-foo",
		"assigner":"assignerId-foo",
		"name":"徐濤",
		"assignee":"assigneeId-foo",
		"id":"fooId"
	}
]
```


-------
## 当用户想取消某个分配时,如果指定的分配记录并不存在,则返回404客户端错误 ##

**用例名称**
delete_shouldReturnErrorWhenAssignmentIsNotExist

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**响应体**
```
{
	"timestamp":1478137472993,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"assignment[fooId] is not found.",
	"path":"/procedures/1/cards/fooId/assignments/fooId",
	"code":404
}
```


-------
## 当用户根据ID查找分配记录时,如果该记录存在则将其返回 ##

**用例名称**
findById_shouldReturnAssignmentSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**响应体**
```
{
	"creationTime":"2016-11-03 09:44:33.013000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/1/procedures",
			"actions":{}
		},
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments/fooId",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/1/cards/fooId",
			"actions":{}
		}
	},
	"author":"authorId-foo",
	"modificationTime":"2016-11-03 09:44:33.013000",
	"cardId":"cardId-foo",
	"assigner":"assignerId-foo",
	"name":"徐濤",
	"assignee":"assigneeId-foo",
	"id":"fooId"
}
```


-------
## 当用户想取消某个分配时,如果指定的分配记录存在,则成功将其取消 ##

**用例名称**
delete_shouldReturnSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**响应体**
```
{
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/1/cards/fooId",
			"actions":{}
		}
	}
}
```


-------
## 成功创建一条分配记录 ##

**用例名称**
assign_shouldReturn201WhenAssigningSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId/assignments

**请求体**
```
{
	"assigner":"assignerId",
	"assignee":"assigneeId"
}
```


**响应体**
```
{
	"creationTime":"2016-11-03 09:44:33.162000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/1/procedures",
			"actions":{}
		},
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments/fooId",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/1/cards/fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:33.162000",
	"cardId":"fooId",
	"assigner":"assignerId",
	"assignee":"assigneeId",
	"id":"fooId"
}
```


-------
# 十二 、用户 #

## 获取头像>用户在获取头像时,如果此前头像已经上传,则获取时则返回此前上传的头像 ##

**用例名称**
loadAvatar

**URL_TEMPLATE**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"_links":{
		"profile":{
			"href":"http://localhost:8007/users/someone/profile",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		}
	}
}
```


-------
## 上传头像>用户上传头像时,如果头像文件大小超过限制,则告知客户端相关错误 ##

**用例名称**
nowAllowedIfAvatarWasTooBig

**URL_TEMPLATE**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"timestamp":1478137494733,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"头像文件已经超过100KB限制。",
	"path":"/users/someone/avatar",
	"code":1004
}
```


-------
## 上传头像>用户上传头像时,如果未传头像文件,则告知客户端相关错误 ##

**用例名称**
nowAllowedIfAvatarWasNull

**URL_TEMPLATE**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"timestamp":1478137494788,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"请上传头像文件。",
	"path":"/users/someone/avatar",
	"code":1003
}
```


-------
## 获取头像>用户在获取头像时,如果此前头像没有上传头像,则返回默认头像 ##

**用例名称**
loadDefaultAvatar

**URL_TEMPLATE**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"_links":{
		"profile":{
			"href":"http://localhost:8007/users/someone/profile",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		}
	}
}
```


-------
## 上传头像>用户成功上传头像 ##

**用例名称**
uploadAvatar

**URL_TEMPLATE**
http://localhost:8007/users/someone/avatar

**响应体**
```
{
	"_links":{
		"profile":{
			"href":"http://localhost:8007/users/someone/profile",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		}
	}
}
```


-------
## 个人资料>用户可以更新个人资料 ##

**用例名称**
updateProfile

**URL_TEMPLATE**
http://localhost:8007/users/someone/profile

**请求体**
```
{
	"nickName":"nick-name"
}
```


**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/users/someone/profile",
			"actions":{}
		},
		"avatar":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		}
	},
	"nickName":"nick-name",
	"userName":"someone",
	"email":"someone@gmail.com"
}
```


-------
## 个人资料>用户登录后,可以获取个人资料 ##

**用例名称**
loadProfile

**URL_TEMPLATE**
http://localhost:8007/users/someone/profile

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/users/someone/profile",
			"actions":{}
		},
		"avatar":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		}
	},
	"nickName":"tao",
	"userName":"someone",
	"email":"someone@gmail.com"
}
```


-------
## 个人资料>用户登录后,可以获取个人资料 ##

**用例名称**
initProfileIfProfileIsNotExist

**URL_TEMPLATE**
http://localhost:8007/users/someone/profile

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/users/someone/profile",
			"actions":{}
		},
		"avatar":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		}
	},
	"userName":"someone",
	"email":"someone@gmail.com"
}
```


-------
# 十三 、验收标准 #

## 创建验收标准>用户创建完卡片后,可以创建为其创建相应的验收标准 ##

**用例名称**
create_shouldReturn201WhenCreateACSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias

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
	"creationTime":"2016-11-03 09:44:31.339000",
	"_links":{
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:31.339000",
	"sortNumber":9999,
	"finished":false,
	"id":"fooId"
}
```


-------
## 调整验收标准的顺序>用户创建完卡片后,可以调整验收标准的排列顺序 ##

**用例名称**
resortAcceptanceCriterias

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/sortNumbers

**请求体**
```
[
	{
		"sortNumber":1,
		"finished":false,
		"id":"acceptanceCriteria-id1"
	},
	{
		"sortNumber":2,
		"finished":false,
		"id":"acceptanceCriteria-id2"
	}
]
```


**响应体**
```
{
	"acceptanceCriterias":[
		{
			"summary":"AC-summary-1",
			"creationTime":"2016-11-03 09:44:31.945000",
			"_links":{
				"acceptanceCriterias":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-id1",
					"actions":{}
				},
				"card":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
					"actions":{}
				}
			},
			"author":"someone",
			"modificationTime":"2016-11-03 09:44:31.945000",
			"sortNumber":1,
			"finished":false,
			"id":"acceptanceCriteria-id1"
		},
		{
			"summary":"AC-summary-2",
			"creationTime":"2016-11-03 09:44:31.945000",
			"_links":{
				"acceptanceCriterias":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-id2",
					"actions":{}
				},
				"card":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
					"actions":{}
				}
			},
			"author":"someone",
			"modificationTime":"2016-11-03 09:44:31.945000",
			"sortNumber":2,
			"finished":false,
			"id":"acceptanceCriteria-id2"
		}
	],
	"_links":{
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
			"actions":{}
		},
		"sortNumbers":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/sortNumbers",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	}
}
```


-------
## 获取指定的验收标准>用户为卡片创建验收标准后,可以根据ID获取指定的验收标准 ##

**用例名称**
loadACById

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId

**响应体**
```
{
	"summary":"AC-summary",
	"creationTime":"2016-11-03 09:44:32.146000",
	"_links":{
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:32.146000",
	"sortNumber":9999,
	"finished":false,
	"id":"fooId"
}
```


-------
## 创建验收标准>如果用户在创建验收标准时,未提供概述,则不允许创建 ##

**用例名称**
notAllowedIfSummaryIsEmpty

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias

**请求体**
```
{
	"summary":"",
	"finished":false
}
```


**响应体**
```
{
	"timestamp":1478137472335,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"验收标准的概述不能为空。",
	"path":"/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
	"code":400
}
```


-------
## 获取指定卡片的验收标准>用户为卡片创建验收标准后,可以查看 ##

**用例名称**
loadAcceptanceCriterias

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias

**响应体**
```
{
	"acceptanceCriterias":[
		{
			"summary":"AC-summary",
			"creationTime":"2016-11-03 09:44:32.607000",
			"_links":{
				"acceptanceCriterias":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId",
					"actions":{}
				},
				"card":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
					"actions":{}
				}
			},
			"author":"someone",
			"modificationTime":"2016-11-03 09:44:32.607000",
			"sortNumber":9999,
			"finished":false,
			"id":"fooId"
		}
	],
	"_links":{
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
			"actions":{}
		},
		"sortNumbers":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/sortNumbers",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	}
}
```


-------
## 删除指定的验收标准>用户为卡片创建验收标准后,可以删除指定的验收标准 ##

**用例名称**
deleteAC

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId

**响应体**
```
{
	"_links":{
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
			"actions":{}
		}
	}
}
```


-------
## 更新指定的验收标准>用户为卡片创建验收标准后,可以更新指定的验收标准 ##

**用例名称**
updateAC

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId

**请求体**
```
{
	"summary":"new-AC-summary",
	"finished":true
}
```


**响应体**
```
{
	"summary":"new-AC-summary",
	"creationTime":"2016-11-03 09:44:32.753000",
	"_links":{
		"acceptanceCriterias":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:32.753000",
	"sortNumber":9999,
	"finished":true,
	"id":"fooId"
}
```


-------
# 十四 、评论 #

## 获取指定卡片的评论>用户在获取卡片的评论时,只可以操作自己的卡片 ##

**用例名称**
loadComments

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments

**响应体**
```
{
	"comments":[
		{
			"summary":"comment-summary",
			"publishTime":"11-01 12:36",
			"creationTime":"2016-11-01 12:36:00.000000",
			"_links":{
				"comments":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId",
					"actions":{}
				},
				"avatar":{
					"href":"http://localhost:8007/users/others/avatar",
					"actions":{}
				},
				"card":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
					"actions":{}
				}
			},
			"author":"others",
			"modificationTime":"2016-11-03 09:44:34.688000",
			"id":"fooId"
		},
		{
			"summary":"comment-summary",
			"publishTime":"11-01 12:36",
			"creationTime":"2016-11-01 12:36:54.000000",
			"_links":{
				"comments":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId1",
					"actions":{
						"delete":{
							"isAllowed":true
						}
					}
				},
				"avatar":{
					"href":"http://localhost:8007/users/someone/avatar",
					"actions":{}
				},
				"card":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
					"actions":{}
				}
			},
			"author":"someone",
			"modificationTime":"2016-11-03 09:44:34.687000",
			"id":"fooId1"
		}
	],
	"_links":{
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	}
}
```


-------
## 删除指定的评论>当用户创建删除评论时,只可以删除由自己撰写的评论 ##

**用例名称**
deleteComment

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId

**响应体**
```
{
	"timestamp":1478137474772,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.AuthenticationException",
	"message":"该条评论并非你所有撰写,你不可以删除。",
	"path":"/procedures/procedures-fooId/cards/card-fooId/comments/fooId",
	"code":5002
}
```


-------
## 撰写评论>用户创建完卡片后,可以创建为其撰写相应的评论 ##

**用例名称**
create_shouldReturn201WhenCreateACSuccessfully

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments

**请求体**
```
{
	"summary":"comment-summary",
	"publishTime":""
}
```


**响应体**
```
{
	"summary":"comment-summary",
	"publishTime":"刚刚",
	"creationTime":"2016-11-03 09:44:34.836000",
	"_links":{
		"comments":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId",
			"actions":{
				"delete":{
					"isAllowed":true
				}
			}
		},
		"avatar":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:34.836000",
	"id":"fooId"
}
```


-------
## 创建评论>如果用户在创建评论时,未提供概述,则不允许创建 ##

**用例名称**
notAllowedIfSummaryIsEmpty

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments

**请求体**
```
{
	"summary":"",
	"publishTime":""
}
```


**响应体**
```
{
	"timestamp":1478137474918,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"验收标准的概述不能为空。",
	"path":"/procedures/procedures-fooId/cards/card-fooId/comments",
	"code":400
}
```


-------
## 获取指定的评论>用户为卡片创建评论后,可以根据ID获取指定的评论 ##

**用例名称**
loadCommentById

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId

**响应体**
```
{
	"summary":"comment-summary",
	"publishTime":"刚刚",
	"creationTime":"2016-11-03 09:44:34.946000",
	"_links":{
		"comments":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId",
			"actions":{
				"delete":{
					"isAllowed":true
				}
			}
		},
		"avatar":{
			"href":"http://localhost:8007/users/someone/avatar",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:34.946000",
	"id":"fooId"
}
```


-------
## 获取指定卡片的评论>用户为卡片创建评论后,可以查看 ##

**用例名称**
loadComments

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments

**响应体**
```
{
	"comments":[
		{
			"summary":"comment-summary",
			"publishTime":"刚刚",
			"creationTime":"2016-11-03 09:44:35.009000",
			"_links":{
				"comments":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
					"actions":{}
				},
				"self":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId",
					"actions":{
						"delete":{
							"isAllowed":true
						}
					}
				},
				"avatar":{
					"href":"http://localhost:8007/users/someone/avatar",
					"actions":{}
				},
				"card":{
					"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
					"actions":{}
				}
			},
			"author":"someone",
			"modificationTime":"2016-11-03 09:44:35.009000",
			"id":"fooId"
		}
	],
	"_links":{
		"self":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
			"actions":{}
		},
		"card":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId",
			"actions":{}
		}
	}
}
```


-------
## 删除指定的评论>用户为卡片创建评论后,可以删除指定的评论 ##

**用例名称**
deleteComment

**URL_TEMPLATE**
http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments/fooId

**响应体**
```
{
	"_links":{
		"comments":{
			"href":"http://localhost:8007/procedures/procedures-fooId/cards/card-fooId/comments",
			"actions":{}
		}
	}
}
```


-------
# 十五 、Worktile #

## 数据导入>数据导入时,新建worktile board ##

**用例名称**
createNewBoard

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"owner":"someone",
	"creationTime":"2016-11-03 09:44:55.332000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{
				"modify":{
					"isAllowed":true
				},
				"read":{
					"isAllowed":true
				},
				"delete":{
					"isAllowed":true
				}
			}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:55.332000",
	"name":"worktile2016/11/03 09:44",
	"id":"fooId"
}
```


-------
## 数据导入>如果文件长度为空,则抛出异常 ##

**用例名称**
throwExceptionIfFileContentIsEmpty

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"timestamp":1478137495448,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"导入文件为空,请重新提交文件。",
	"path":"/someone/worktileTasks",
	"code":3001
}
```


-------
## 数据导入>如果文件没有拓展名,则抛出异常 ##

**用例名称**
throwExceptionIfNoExtensionNameWasFound

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"timestamp":1478137495514,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"文件名称错误,请确保文件名及拓展名完整。",
	"path":"/someone/worktileTasks",
	"code":3004
}
```


-------
## 数据导入>如果文件类型错误,则抛出异常 ##

**用例名称**
throwExceptionIfFileFormatIsInvalid

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"timestamp":1478137495603,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"文件类型错误,请上传以.json结尾的文本文件",
	"path":"/someone/worktileTasks",
	"code":3005
}
```


-------
## 数据导入>如果文件内容格式错误,则抛出异常 ##

**用例名称**
throwExceptionIfFileContentFormatIsInvalid

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"timestamp":1478137495704,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"文件内容格式错误,请检查修复后重新提交文件。",
	"path":"/someone/worktileTasks",
	"code":3002
}
```


-------
## 数据导入>新建board后,导入tasks ##

**用例名称**
importCards

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"owner":"someone",
	"creationTime":"2016-11-03 09:44:55.774000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{
				"modify":{
					"isAllowed":true
				},
				"read":{
					"isAllowed":true
				},
				"delete":{
					"isAllowed":true
				}
			}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:55.774000",
	"name":"worktile2016/11/03 09:44",
	"id":"fooId"
}
```


-------
## 数据导入>新建board后,导入entry ##

**用例名称**
importEntry

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"owner":"someone",
	"creationTime":"2016-11-03 09:44:55.897000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{
				"modify":{
					"isAllowed":true
				},
				"read":{
					"isAllowed":true
				},
				"delete":{
					"isAllowed":true
				}
			}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:55.897000",
	"name":"worktile2016/11/03 09:44",
	"id":"fooId"
}
```


-------
## 数据导入>新建tasks后,导入todos ##

**用例名称**
importTodos

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"owner":"someone",
	"creationTime":"2016-11-03 09:44:56.016000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards",
			"actions":{}
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures",
			"actions":{}
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId",
			"actions":{
				"modify":{
					"isAllowed":true
				},
				"read":{
					"isAllowed":true
				},
				"delete":{
					"isAllowed":true
				}
			}
		}
	},
	"author":"someone",
	"modificationTime":"2016-11-03 09:44:56.016000",
	"name":"worktile2016/11/03 09:44",
	"id":"fooId"
}
```


-------
## 数据导入>如果未上传文件,则抛出异常 ##

**用例名称**
throwExceptionIfNoFileWasUpload

**URL_TEMPLATE**
http://localhost:8007/someone/worktileTasks

**响应体**
```
{
	"timestamp":1478137496089,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"未上传文件!",
	"path":"/someone/worktileTasks",
	"code":3007
}
```


-------
# 领域未定义 #

## 当头部信息的userName和路径中的不一致时,告知客户端错误 ##

**用例名称**
throwExceptionIfUserNameInHeaderIsNotEqualWithItInPath

**URL_TEMPLATE**
http://localhost:8007/thief/teams

**请求体**
```
{
	"name":"teamName"
}
```


**响应体**
```
{
	"timestamp":1478137475235,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"请求头部的用户名与URL中的不一致,请求可能被篡改。",
	"path":"/thief/teams",
	"code":-99
}
```


-------
## 当token不为空且未失效时,请求到达后更新token的有效期 ##

**用例名称**
shouldUpdateTokenExpiredTime

**URL_TEMPLATE**
http://localhost:8007/procedures/1/cards/fooId

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1478137481136,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"卡片未找到。",
	"path":"/procedures/1/cards/fooId",
	"code":5001
}
```


-------
## 如果用户在5分钟内未发送请求,token将会失效,告知客户端需要重新授权 ##

**用例名称**
shouldReturnTimeOut

**URL_TEMPLATE**
http://localhost:8007/resource

**响应体**
```
{
	"timestamp":1478137481166,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"认证已经过期,请重新认证获取token.",
	"path":"/resource",
	"code":1102
}
```


-------
## 当请求需要认证时,如果没有携带token,则告知客户端需要授权 ##

**用例名称**
shouldReturn401WhenAuthIsRequired

**URL_TEMPLATE**
http://localhost:8007/resource

**响应体**
```
{
	"timestamp":1478137481195,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"当前用户未认证,请先登录认证。",
	"path":"/resource",
	"code":1101
}
```


-------
## 当token中的用户名与header中携带的用户名不一致时,告知客户端认证未通过 ##

**用例名称**
shouldAuthenticatedFailedWhenUserNameIsNotConsistent

**URL_TEMPLATE**
http://localhost:8007/resource

**响应体**
```
{
	"timestamp":1478137481232,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"请求头部的用户名与token中的不一致,请求可能被篡改。",
	"path":"/resource",
	"code":1103
}
```


-------
## 当用户请求登录或注册时,首先需要向系统发送一次认证请求,将公钥发送至客户端 ##

**用例名称**
identification_askForAuthenticationWhenUserIsExists

**URL_TEMPLATE**
http://localhost:8007/publicKey

**响应体**
```
{
	"_links":{
		"registration":{
			"href":"http://localhost:8007/registration",
			"actions":{}
		},
		"login":{
			"href":"http://localhost:8007/login",
			"actions":{}
		}
	},
	"publicKey":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvt2Q1aDhixfv0VWZTEzVYmf4QQtVMSwSC1bYociaw/dgGaQY/c+bcdubcY5LrZdaj9BPJApGvEIQGnXDDIURXW8p5w+xZ6ntbb8vextGg38TD3MasCpcabb18bBsi/hiEVgSxGL4yZtR7gtwA5aTQbzDxii9j27vAVQX6ZGiG4QIDAQAB\r\n"
}
```


-------