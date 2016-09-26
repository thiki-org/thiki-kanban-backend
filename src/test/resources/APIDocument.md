
# 零、入口 #

## 初次访问系统时入口 ##

**用例名称**
enter_shouldReturnEntranceSuccessfully

**URL**
http://localhost:8007/entrance

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/entrance"
		},
		"publicKey":{
			"href":"http://localhost:8007/publicKey"
		},
		"passwordRetrievalApplication":{
			"href":"http://localhost:8007/passwordRetrievalApplication"
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

**URL**
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
	"timestamp":1474696305730,
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

**URL**
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
	"timestamp":1474696305804,
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

**URL**
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
	"timestamp":1474696305894,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邮箱已经存在,请使用其他邮箱。",
	"path":"/registration",
	"code":1001
}
```


-------
## 注册成功>根据服务端提供的公钥对密码进行加密,服务端拿到加密的密码后,首先用私钥解密,再通过MD5算法加盐加密 ##

**用例名称**
registerNewUser_shouldReturn201WhenRegisterSuccessfully

**URL**
http://localhost:8007/registration

**请求体**
```
{
	"name":"someone",
	"password":"oVyLQ8weDYErEIZQcyMNAOaLrS9gqvoU3BParHxsA61HLG1/wOg+idtgf70m8Z9MUh3WNR5kc2J8wHm0sGZu4HivHFXrA0K1b9bCdWb0CxyfwmqMvj9y/dgvcIgD3QKTDP+0+zzKqNXuVLHr2JLNxIcvyrQWn0tUr7fo464ymm0=",
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
			"href":"http://localhost:8007/login"
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

**URL**
http://localhost:8007/login?identity=someone&password=aJqwTNXWeQMVCwmuzg1aL3u%2BxdodAOwB0DCxrH4%2BZ2oqSYpEsULzBO2g40D29dmBFUakaUGhw5UMh4jh0tGed%2BgrHmz1HzIasJvpYKWWmZ1aMcCQQFqjKyCmMQ8nmpOdznJtkDyEWlpknTxYdOK0UPXLs8BPbEFHhjortWLIIWw%3D

**响应体**
```
{
	"timestamp":1474696300587,
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

**URL**
http://localhost:8007/login?identity=someone&password=N8EV0fVAPlImAqyOX%2FSEF2ycJS9kkqzRFnptGuIWIkewo0mqv5FxyNGwDEJPeEG8%2BHwKwFuUwKKssChsJQdQLPoJ1oHLXQAd6m2KZN9TSUy%2BWlILpzKoWGHVo4vXgW7QCHxTKDMrIfOKv%2FnAhavnkQVDPiHAT7zeyuXa%2F2O4jOo%3D

**响应体**
```
{
	"_links":{
		"teams":{
			"href":"http://localhost:8007/someone/teams"
		},
		"unreadNotificationsTotal":{
			"href":"http://localhost:8007/someone/notifications/unread/total"
		},
		"boards":{
			"href":"http://localhost:8007/someone/boards"
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

**URL**
http://localhost:8007/login?password=foo

**响应体**
```
{
	"timestamp":1474696300767,
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

**URL**
http://localhost:8007/login?identity=foo&password=foo

**响应体**
```
{
	"timestamp":1474696300848,
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

## 成功更新一个board信息 ##

**用例名称**
shouldUpdateSuccessfully

**URL**
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
	"creationTime":"2016-09-24 13:51:32.152000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards"
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures"
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:32.152000",
	"name":"new-name",
	"id":"fooId"
}
```


-------
## 当更新一个board时,如果存在同名,则不允许更新,并告知客户端错误信息 ##

**用例名称**
UpdateIsNotAllowedIfBoardWithSameNameIsAlreadyExists

**URL**
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
	"timestamp":1474696292332,
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

**URL**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"creationTime":"2016-09-24 13:51:32.363000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards"
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures"
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:32.363000",
	"name":"board-name",
	"id":"fooId"
}
```


-------
## 当用户创建一个board时,如果存在同名,则不允许创建,并告知客户端错误信息 ##

**用例名称**
NotAllowedIfBoardWithSameNameIsAlreadyExists

**URL**
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
	"timestamp":1474696292716,
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

**URL**
http://localhost:8007/someone/boards

**响应体**
```
[
	{
		"creationTime":"2016-09-24 13:51:32.739000",
		"_links":{
			"all":{
				"href":"http://localhost:8007/someone/boards"
			},
			"procedures":{
				"href":"http://localhost:8007/boards/fooId/procedures"
			},
			"self":{
				"href":"http://localhost:8007/someone/boards/fooId"
			}
		},
		"author":"someone",
		"modificationTime":"2016-09-24 13:51:32.739000",
		"name":"board-name",
		"id":"fooId"
	}
]
```


-------
## 当创建一个board时,如果参数合法,则创建成功并返回创建后的board ##

**用例名称**
shouldReturn201WhenCreateBoardSuccessfully

**URL**
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
	"creationTime":"2016-09-24 13:51:32.847000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards"
		},
		"procedures":{
			"href":"http://localhost:8007/boards/fooId/procedures"
		},
		"self":{
			"href":"http://localhost:8007/someone/boards/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:32.847000",
	"name":"board-name",
	"id":"fooId"
}
```


-------
## 当用户删除一个指定的board时,如果该board存在,则删除成功 ##

**用例名称**
shouldDeleteSuccessfullyWhenTheBoardIsExist

**URL**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"_links":{
		"all":{
			"href":"http://localhost:8007/someone/boards"
		}
	}
}
```


-------
## 当看板不存在时,则不允许更新 ##

**用例名称**
shouldUpdateFailedWhenTheBoardIsNotExist

**URL**
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
	"timestamp":1474696292961,
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

**URL**
http://localhost:8007/someone/boards/fooId

**响应体**
```
{
	"timestamp":1474696292996,
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

## 创建一个新的procedure,如果它并不是指定boardId下第一个procedure,则其排序号应根据当前procedure数量自动增加 ##

**用例名称**
create_orderNumberShouldAutoIncrease

**URL**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"title":"title."
}
```


**响应体**
```
{
	"orderNumber":1,
	"creationTime":"2016-09-24 13:51:44.630000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards"
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId"
		}
	},
	"author":"fooName",
	"modificationTime":"2016-09-24 13:51:44.630000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"title."
}
```


-------
## 更新procedure时,如果参数合法且待更新的procedure存在,则更新成功 ##

**用例名称**
shouldUpdateSuccessfully

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**请求体**
```
{
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
{
	"orderNumber":0,
	"creationTime":"2016-09-24 13:51:44.751000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards"
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId"
		}
	},
	"author":"1",
	"modificationTime":"2016-09-24 13:51:44.751000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"newTitle"
}
```


-------
## 当移动一个procedure时,移动后的排序小于其原先的排序 ##

**用例名称**
update_shouldResortSuccessfullyWhenCurrentSortNumberIsLessThanOriginNumber

**URL**
http://localhost:8007/boards/feeId/procedures/fooId2

**请求体**
```
{
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
{
	"orderNumber":0,
	"creationTime":"2016-09-24 13:51:44.845000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId2/cards"
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId2"
		}
	},
	"author":"1",
	"modificationTime":"2016-09-24 13:51:44.845000",
	"boardId":"feeId",
	"id":"fooId2",
	"title":"newTitle"
}
```


-------
## 当根据procedureId查找procedure时,如果procedure存在,则将其返回 ##

**用例名称**
shouldReturnProcedureWhenFindProcedureById

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**响应体**
```
{
	"orderNumber":0,
	"creationTime":"2016-09-24 13:51:44.925000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards"
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId"
		}
	},
	"author":"1",
	"modificationTime":"2016-09-24 13:51:44.925000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"this is the first procedure."
}
```


-------
## 创建新的procedure时,如果名称为空,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldFailedIfProcedureTitleIsEmpty

**URL**
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
	"timestamp":1474696305015,
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

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**请求体**
```
{
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
{
	"timestamp":1474696305087,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[fooId] is not found.",
	"path":"/boards/feeId/procedures/fooId",
	"code":404
}
```


-------
## 通过boardId获取所有的procedure ##

**用例名称**
shouldReturnAllEntriesSuccessfully

**URL**
http://localhost:8007/boards/feeId/procedures

**响应体**
```
[
	{
		"orderNumber":0,
		"creationTime":"2016-09-24 13:51:45.113000",
		"_links":{
			"all":{
				"href":"http://localhost:8007/boards/feeId/procedures"
			},
			"cards":{
				"href":"http://localhost:8007/procedures/fooId/cards"
			},
			"self":{
				"href":"http://localhost:8007/boards/feeId/procedures/fooId"
			}
		},
		"author":"tao",
		"modificationTime":"2016-09-24 13:51:45.113000",
		"boardId":"feeId",
		"id":"fooId",
		"title":"this is the first procedure."
	}
]
```


-------
## 创建新的procedure时,如果名称长度超限,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsTooLong

**URL**
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
	"timestamp":1474696305188,
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

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**响应体**
```
{
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures"
		}
	}
}
```


-------
## 创建新的procedure时,如果名称为空字符串,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsEmpty

**URL**
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
	"timestamp":1474696305280,
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

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**响应体**
```
{
	"timestamp":1474696305337,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[fooId] is not found.",
	"path":"/boards/feeId/procedures/fooId",
	"code":404
}
```


-------
## 当移动一个procedure时,移动后的排序大于其原先的排序 ##

**用例名称**
update_shouldResortSuccessfullyWhenCurrentSortNumberIsMoreThanOriginNumber

**URL**
http://localhost:8007/boards/feeId/procedures/fooId1

**请求体**
```
{
	"orderNumber":2,
	"title":"newTitle"
}
```


**响应体**
```
{
	"orderNumber":2,
	"creationTime":"2016-09-24 13:51:45.369000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId1/cards"
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId1"
		}
	},
	"author":"1",
	"modificationTime":"2016-09-24 13:51:45.369000",
	"boardId":"feeId",
	"id":"fooId1",
	"title":"newTitle"
}
```


-------
## 创建新的procedure时,同一看板下已经存在同名,则不允许创建并返回客户端400错误 ##

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsAlreadyExits

**URL**
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
	"timestamp":1474696305468,
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

**URL**
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
	"orderNumber":0,
	"creationTime":"2016-09-24 13:51:45.530000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/feeId/procedures"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards"
		},
		"self":{
			"href":"http://localhost:8007/boards/feeId/procedures/fooId"
		}
	},
	"author":"fooName",
	"modificationTime":"2016-09-24 13:51:45.530000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"this is the procedure title."
}
```


-------
# 五、卡片 #

## 当移动一个卡片时,移动后的顺序大于初始顺序 ##

**用例名称**
update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumber

**URL**
http://localhost:8007/procedures/1/cards/fooId2

**请求体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"procedureId":"1"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-24 13:51:33.025000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId2/assignments"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId2"
		}
	},
	"modificationTime":"2016-09-24 13:51:33.025000",
	"id":"fooId2",
	"procedureId":"1"
}
```


-------
## 当更新一个卡片时,如果待更新的卡片不存在,则抛出资源不存在的错误 ##

**用例名称**
update_shouldThrowResourceNotFoundExceptionWhenCardToUpdateIsNotExist

**URL**
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
	"timestamp":1474696293220,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[fooId] is not found.",
	"path":"/procedures/1/cards/fooId",
	"code":404
}
```


-------
## 当创建一个卡片时,如果卡片概述长度超过50,则创建失败 ##

**用例名称**
create_shouldFailedIfSummaryIsTooLong

**URL**
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
	"timestamp":1474696293289,
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

**URL**
http://localhost:8007/procedures/1/cards/1

**响应体**
```
{
	"summary":"this is the card summary.",
	"orderNumber":0,
	"creationTime":"2016-09-24 13:51:33.308000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/1/assignments"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/1"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:33.308000",
	"id":"1",
	"procedureId":"1",
	"content":"play badminton"
}
```


-------
## 当一个卡片从某个procedure移动到另一个procedure时,不仅需要重新排序目标procedure,也要对原始procedure排序 ##

**用例名称**
update_shouldResortSuccessfullyWhenCardIsFromAntherProcedure

**URL**
http://localhost:8007/procedures/1/cards/fooId6

**请求体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"procedureId":"1"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-24 13:51:33.389000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId6/assignments"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId6"
		}
	},
	"modificationTime":"2016-09-24 13:51:33.389000",
	"id":"fooId6",
	"procedureId":"1"
}
```


-------
## 当移动一个卡片时,移动后的顺序小于其前置顺序 ##

**用例名称**
update_shouldResortSuccessfullyWhenCurrentOrderNumberLessThanOriginNumber

**URL**
http://localhost:8007/procedures/1/cards/fooId4

**请求体**
```
{
	"summary":"newSummary",
	"orderNumber":1,
	"procedureId":"1"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":1,
	"creationTime":"2016-09-24 13:51:33.488000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId4/assignments"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId4"
		}
	},
	"modificationTime":"2016-09-24 13:51:33.488000",
	"id":"fooId4",
	"procedureId":"1"
}
```


-------
## 根据ID查找一个卡片时,如果卡片不存在,则抛出404的错误 ##

**用例名称**
update_shouldFailedWhenCardIsNotExist

**URL**
http://localhost:8007/procedures/fooId/cards/feeId

**响应体**
```
{
	"timestamp":1474696293572,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[feeId] is not found.",
	"path":"/procedures/fooId/cards/feeId",
	"code":404
}
```


-------
## 更新卡片成功 ##

**用例名称**
update_shouldReturn200WhenUpdateCardSuccessfully

**URL**
http://localhost:8007/procedures/1/cards/fooId

**请求体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"procedureId":"1"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-24 13:51:33.595000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId"
		}
	},
	"modificationTime":"2016-09-24 13:51:33.595000",
	"id":"fooId",
	"procedureId":"1"
}
```


-------
## 当删除一个卡片时,如果待删除的卡片不存在,则抛出404错误 ##

**用例名称**
delete_shouldDeleteFailedWhenTheCardIsNotExist

**URL**
http://localhost:8007/procedures/feeId/cards/non-exists-cardId

**响应体**
```
{
	"timestamp":1474696293694,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[non-exists-cardId] is not found.",
	"path":"/procedures/feeId/cards/non-exists-cardId",
	"code":404
}
```


-------
## 当根据procedureId查找其下属的卡片时,可以返回其所有卡片 ##

**用例名称**
shouldReturnCardsWhenFindCardsByProcedureIdSuccessfully

**URL**
http://localhost:8007/procedures/fooId/cards

**响应体**
```
[
	{
		"summary":"this is the card summary.",
		"orderNumber":0,
		"creationTime":"2016-09-24 13:51:33.715000",
		"_links":{
			"assignments":{
				"href":"http://localhost:8007/procedures/fooId/cards/1/assignments"
			},
			"cards":{
				"href":"http://localhost:8007/procedures/fooId/cards"
			},
			"self":{
				"href":"http://localhost:8007/procedures/fooId/cards/1"
			}
		},
		"author":"someone",
		"modificationTime":"2016-09-24 13:51:33.715000",
		"id":"1",
		"procedureId":"fooId",
		"content":"play badminton"
	}
]
```


-------
## 当创建一个卡片时,如果卡片所属的procedure并不存在,则创建失败 ##

**用例名称**
create_shouldCreateFailedWhenProcedureIsNotFound

**URL**
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
	"timestamp":1474696293798,
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

**URL**
http://localhost:8007/procedures/feeId/cards/fooId

**响应体**
```
{
	"_links":{
		"cards":{
			"href":"http://localhost:8007/procedures/feeId/cards"
		}
	}
}
```


-------
## 创建一个新的卡片 ##

**用例名称**
create_shouldReturn201WhenCreateCardSuccessfully

**URL**
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
	"orderNumber":0,
	"creationTime":"2016-09-24 13:51:33.894000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/fooId/cards/fooId/assignments"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/fooId/cards"
		},
		"self":{
			"href":"http://localhost:8007/procedures/fooId/cards/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:33.894000",
	"id":"fooId",
	"procedureId":"fooId"
}
```


-------
## 当创建一个卡片时,如果卡片概述为空,则创建失败 ##

**用例名称**
create_shouldFailedIfSummaryIsNull

**URL**
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
	"timestamp":1474696293967,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"卡片概述不能为空。",
	"path":"/procedures/fooId/cards",
	"code":400
}
```


-------
## 当移动一个卡片时,卡片移动后的序号大于其前置序号,但在procedure中它移动后的序号并不是最大的。 ##

**用例名称**
update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumberButNotTheBiggest

**URL**
http://localhost:8007/procedures/1/cards/fooId1

**请求体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"procedureId":"1"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-24 13:51:33.985000",
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId1/assignments"
		},
		"cards":{
			"href":"http://localhost:8007/procedures/1/cards"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId1"
		}
	},
	"modificationTime":"2016-09-24 13:51:33.985000",
	"id":"fooId1",
	"procedureId":"1"
}
```


-------
## 当根据procedureID查找卡片时,如果procedure不存在,则抛出404异常 ##

**用例名称**
findCardsByProcedureId_shouldReturn404WhenProcedureIsNotFound

**URL**
http://localhost:8007/procedures/2/cards

**响应体**
```
{
	"timestamp":1474696294078,
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

## 创建团队时，如果团队名称为空，则不允许创建 ##

**用例名称**
creationIsNotAllowedIfTeamNameIsEmpty

**URL**
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
	"timestamp":1474696313235,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"团队名称不可以为空。",
	"path":"/someone/teams",
	"code":400
}
```


-------
## 用户根据ID获取team时,如果该team存在,则返回其信息 ##

**用例名称**
shouldReturnBoardWhenTeamIsExist

**URL**
http://localhost:8007/teams/fooId

**响应体**
```
{
	"creationTime":"2016-09-24 13:51:53.291000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:53.291000",
	"name":"team-name",
	"id":"fooId"
}
```


-------
## 创建团队时，如果团队名称超限，则不允许创建 ##

**用例名称**
creationIsNotAllowedIfTeamNameIsTooLong

**URL**
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
	"timestamp":1474696313402,
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

**URL**
http://localhost:8007/someone/teams

**响应体**
```
[
	{
		"creationTime":"2016-09-24 13:51:53.453000",
		"_links":{
			"members":{
				"href":"http://localhost:8007/teams/fooId/members"
			},
			"self":{
				"href":"http://localhost:8007/teams/fooId"
			}
		},
		"author":"someone",
		"modificationTime":"2016-09-24 13:51:53.453000",
		"name":"team-name",
		"id":"fooId"
	}
]
```


-------
## 创建一个团队 ##

**用例名称**
create_shouldReturn201WhenCreateTeamSuccessfully

**URL**
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
	"creationTime":"2016-09-24 13:51:53.546000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:53.546000",
	"name":"思奇团队讨论组",
	"id":"fooId"
}
```


-------
## 创建团队时，如果在本人名下已经存在相同名称的团队，则不允许创建 ##

**用例名称**
creationIsNotAllowedIfTeamNameIsConflict

**URL**
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
	"timestamp":1474696313621,
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

**URL**
http://localhost:8007/someone/teams

**请求体**
```
{
}
```


**响应体**
```
{
	"timestamp":1474696313718,
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

**URL**
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
	"timestamp":1474696306527,
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

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"timestamp":1474696306585,
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

**URL**
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
	"creationTime":"2016-09-24 13:51:46.697000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 13:51:46.697000",
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

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"timestamp":1474696308350,
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

**URL**
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
	"timestamp":1474696308420,
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

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"timestamp":1474696308467,
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

**URL**
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
	"creationTime":"2016-09-24 13:51:48.561000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 13:51:48.561000",
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

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"creationTime":"2016-09-24 13:51:50.085000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 13:51:50.085000",
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

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id

**响应体**
```
{
	"creationTime":"2016-09-24 13:51:50.165000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 13:51:50.165000",
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

**URL**
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
	"creationTime":"2016-09-24 13:51:50.305000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 13:51:50.305000",
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

**URL**
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
	"timestamp":1474696311709,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"被邀请的成员不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":7002
}
```


-------
## 如果邀请人并非团队的成员则不允许发送邀请 ##

**用例名称**
NotAllowedIfInviterIsNotAMemberOfTheTeam

**URL**
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
	"timestamp":1474696311788,
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

**URL**
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
	"creationTime":"2016-09-24 13:51:51.868000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 13:51:51.868000",
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

**URL**
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
	"timestamp":1474696313146,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":7001
}
```


-------
# 八、团队成员 #

## 加入团队时,如果待加入的成员已经在团队中,则不允许加入 ##

**用例名称**
joinTeam_shouldReturnFailedIfMemberIsAlreadyIn

**URL**
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
	"timestamp":1474696306083,
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

**URL**
http://localhost:8007/teams/foo-teamId/members

**响应体**
```
{
	"_links":{
		"invitation":{
			"href":"http://localhost:8007/teams/foo-teamId/members/invitation"
		}
	},
	"members":[
		{
			"_links":{},
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

**URL**
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
	"timestamp":1474696306196,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"Team foo-teamId is not found.",
	"path":"/teams/foo-teamId/teamMembers",
	"code":400
}
```


-------
## 若当前用户并非团队成员，则不允许获取 ##

**用例名称**
NotAllowedIfCurrentUserIsNotAMemberOfTheTeamWhenLoadingTeamMembersByTeamId

**URL**
http://localhost:8007/teams/foo-teamId/members

**响应体**
```
{
	"timestamp":1474696306251,
	"status":401,
	"error":"Unauthorized",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"当前用户非该团队成员,允许获取。",
	"path":"/teams/foo-teamId/members",
	"code":8001
}
```


-------
## 加入一个团队 ##

**用例名称**
joinTeam_shouldReturn201WhenJoinTeamSuccessfully

**URL**
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
	"creationTime":"2016-09-24 13:51:46.319000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-teamId/teamMembers"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 13:51:46.319000",
	"teamId":"foo-teamId",
	"member":"someone",
	"id":"fooId"
}
```


-------
## 当用户加入一个团队后，可以获取该团队的所有成员。但是当团队不存在时,则不允许获取。 ##

**用例名称**
NotAllowedIfTeamIsNotExitsWhenLoadingTeamMembersByTeamId

**URL**
http://localhost:8007/teams/foo-teamId/members

**响应体**
```
{
	"timestamp":1474696306400,
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

**URL**
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
	"timestamp":1474696301284,
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

**URL**
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
	"timestamp":1474696301358,
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

**URL**
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
			"href":"http://localhost:8007/tao/password"
		}
	}
}
```


-------
## 当用户请求找回密码时,需要提供邮箱,如果未提供则告知客户端错误 ##

**用例名称**
NotAllowedIfEmailIsNotProvide

**URL**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
}
```


**响应体**
```
{
	"timestamp":1474696301470,
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

**URL**
http://localhost:8007/tao/password

**请求体**
```
{
	"password":"Qwv72VMklCBfvy3TptZIzzMRVPotfwB/PE/OviycVNEMFVtuF/noXDAquYQwwJ5bAN/L1NDdD/EJAwhQ/BqShIwTHjD9pMVhvpOjz7ulLJ4cHnUhtX25PVNX7nElR+09fjQoDW1tbx6jQ8f257u3lqkbejw1HCS1XHRLk/CJ8S0="
}
```


**响应体**
```
{
	"_links":{
		"login":{
			"href":"http://localhost:8007/login"
		}
	}
}
```


-------
## 邮箱通过格式校验且存在后，创建密码找回申请记前,如果存在未完成的申请,则将其废弃 ##

**用例名称**
discardingUnfinishedPasswordRetrievalApplication

**URL**
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
			"href":"http://localhost:8007/tao/passwordResetApplication"
		}
	}
}
```


-------
## 验证码超过五分钟后,验证失败 ##

**用例名称**
verificationCodeTimeOut

**URL**
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
	"timestamp":1474696303106,
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

**URL**
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
			"href":"http://localhost:8007/tao/passwordResetApplication"
		}
	}
}
```


-------
## 当用户请求找回密码时,需要提供邮箱,如果邮箱不存在则告知客户端错误 ##

**用例名称**
NotAllowedIfEmailFormatIsNotExists

**URL**
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
	"timestamp":1474696304431,
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

**URL**
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
	"timestamp":1474696304484,
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

**URL**
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
	"timestamp":1474696304545,
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

**URL**
http://localhost:8007/someone/notifications

**响应体**
```
{
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications"
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications"
		}
	},
	"notifications":[
		{
			"displayTime":"刚刚",
			"creationTime":"2016-09-24 13:51:40.883000",
			"receiver":"someone",
			"sender":"sender@gmail.com",
			"_links":{
				"self":{
					"href":"http://localhost:8007/someone/notifications/foo-notification-id"
				},
				"notifications":{
					"href":"http://localhost:8007/someone/notifications"
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

**URL**
http://localhost:8007/someone/notifications/foo-notification-id

**响应体**
```
{
	"displayTime":"刚刚",
	"creationTime":"2016-09-24 13:51:40.975000",
	"receiver":"someone",
	"sender":"sender@gmail.com",
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications/foo-notification-id"
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications"
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

**URL**
http://localhost:8007/someone/notifications/unread/total

**响应体**
```
{
	"unreadNotificationsTotal":1,
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications/unread/total"
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications"
		}
	}
}
```


-------
## 获取指定消息>查看某条具体的消息时，如果该消息已读，则加载后不必再设置为已读 ##

**用例名称**
notSetNotificationReadIfItHasAlreadyBeenReadAfterLoading

**URL**
http://localhost:8007/someone/notifications/foo-notification-id

**响应体**
```
{
	"displayTime":"刚刚",
	"creationTime":"2016-09-24 13:51:41.120000",
	"receiver":"someone",
	"sender":"sender@gmail.com",
	"_links":{
		"self":{
			"href":"http://localhost:8007/someone/notifications/foo-notification-id"
		},
		"notifications":{
			"href":"http://localhost:8007/someone/notifications"
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

**URL**
http://localhost:8007/procedures/1/cards/cardId-foo/assignments

**响应体**
```
{
	"timestamp":1474696290952,
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

**URL**
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

**URL**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**响应体**
```
{
	"timestamp":1474696291751,
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

**URL**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**响应体**
```
{
	"creationTime":"2016-09-24 13:51:31.784000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/1/procedures"
		},
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments/fooId"
		},
		"card":{
			"href":"http://localhost:8007/procedures/1/cards/fooId"
		}
	},
	"author":"authorId-foo",
	"modificationTime":"2016-09-24 13:51:31.784000",
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

**URL**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**响应体**
```
{
	"_links":{
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments"
		},
		"card":{
			"href":"http://localhost:8007/procedures/1/cards/fooId"
		}
	}
}
```


-------
## 成功创建一条分配记录 ##

**用例名称**
assign_shouldReturn201WhenAssigningSuccessfully

**URL**
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
	"creationTime":"2016-09-24 13:51:32.066000",
	"_links":{
		"all":{
			"href":"http://localhost:8007/boards/1/procedures"
		},
		"assignments":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments"
		},
		"self":{
			"href":"http://localhost:8007/procedures/1/cards/fooId/assignments/fooId"
		},
		"card":{
			"href":"http://localhost:8007/procedures/1/cards/fooId"
		}
	},
	"author":"11222",
	"modificationTime":"2016-09-24 13:51:32.066000",
	"cardId":"fooId",
	"assigner":"assignerId",
	"assignee":"assigneeId",
	"id":"fooId"
}
```


-------
# 领域未定义 #

## 当头部信息的userName和路径中的不一致时,告知客户端错误 ##

**用例名称**
throwExceptionIfUserNameInHeaderIsNotEqualWithItInPath

**URL**
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
	"timestamp":1474696294228,
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

**URL**
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
	"timestamp":1474696300359,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[fooId] is not found.",
	"path":"/procedures/1/cards/fooId",
	"code":404
}
```


-------
## 如果用户在5分钟内未发送请求,token将会失效,告知客户端需要重新授权 ##

**用例名称**
shouldReturnTimeOut

**URL**
http://localhost:8007/resource

**响应体**
```
{
	"timestamp":1474696300395,
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

**URL**
http://localhost:8007/resource

**响应体**
```
{
	"timestamp":1474696300422,
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

**URL**
http://localhost:8007/resource

**响应体**
```
{
	"timestamp":1474696300474,
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

**URL**
http://localhost:8007/publicKey

**响应体**
```
{
	"_links":{
		"registration":{
			"href":"http://localhost:8007/registration"
		},
		"login":{
			"href":"http://localhost:8007/login"
		}
	},
	"publicKey":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvt2Q1aDhixfv0VWZTEzVYmf4QQtVMSwSC1bYociaw/dgGaQY/c+bcdubcY5LrZdaj9BPJApGvEIQGnXDDIURXW8p5w+xZ6ntbb8vextGg38TD3MasCpcabb18bBsi/hiEVgSxGL4yZtR7gtwA5aTQbzDxii9j27vAVQX6ZGiG4QIDAQAB\r\n"
}
```


-------