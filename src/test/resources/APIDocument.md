
### 场景 ###
当用户根据cardID获取分配记录时,如果指定的卡片并不存在,则返回404客户端错误

**用例名称**
findByCardId_shouldReturnErrorWhenCardIsNotExist

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
null
```


**响应体**
```
{
	"timestamp":1473856896699,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"卡片未找到。",
	"path":"/procedures/1/cards/cardId-foo/assignments",
	"code":40011
}
```


-------
### 场景 ###
当用户根据cardID获取分配记录时,如果指定的卡片存在,则返回分配记录集合

**用例名称**
findByCardId_shouldReturnAssignmentsSuccessfully

**URL**
http://localhost:8007/procedures/1/cards/cardId-foo/assignments

**请求体**
```
null
```


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
### 场景 ###
当用户想取消某个分配时,如果指定的分配记录并不存在,则返回404客户端错误

**用例名称**
delete_shouldReturnErrorWhenAssignmentIsNotExist

**URL**
http://localhost:8007/error/404

**请求体**
```
null
```


**响应体**
```
{
	"timestamp":1473856897388,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"assignment[fooId] is not found.",
	"path":"/procedures/1/cards/fooId/assignments/fooId",
	"code":404
}
```


-------
### 场景 ###
当用户根据ID查找分配记录时,如果该记录存在则将其返回

**用例名称**
findById_shouldReturnAssignmentSuccessfully

**URL**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**请求体**
```
null
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:37.408000",
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
	"modificationTime":"2016-09-14 20:41:37.408000",
	"cardId":"cardId-foo",
	"assigner":"assignerId-foo",
	"name":"徐濤",
	"assignee":"assigneeId-foo",
	"id":"fooId"
}
```


-------
### 场景 ###
当用户想取消某个分配时,如果指定的分配记录存在,则成功将其取消

**用例名称**
delete_shouldReturnSuccessfully

**URL**
http://localhost:8007/procedures/1/cards/fooId/assignments/fooId

**请求体**
```
null
```


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
### 场景 ###
成功创建一条分配记录

**用例名称**
assign_shouldReturn201WhenAssigningSuccessfully

**URL**
http://localhost:8007/procedures/1/cards/fooId/assignments

**请求体**
```
{
	"assignee":"assigneeId",
	"assigner":"assignerId",
	"author":"11222",
	"cardId":"fooId",
	"id":"fooId"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:37.659000",
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
	"modificationTime":"2016-09-14 20:41:37.659000",
	"cardId":"fooId",
	"assigner":"assignerId",
	"assignee":"assigneeId",
	"id":"fooId"
}
```


-------
### 场景 ###
成功更新一个board信息

**用例名称**
shouldUpdateSuccessfully

**URL**
http://localhost:8007/someone/boards/fooId

**请求体**
```
{
	"id":"fooId",
	"name":"new-name"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:37.723000",
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
	"modificationTime":"2016-09-14 20:41:37.723000",
	"name":"new-name",
	"id":"fooId"
}
```


-------
### 场景 ###
当更新一个board时,如果存在同名,则不允许更新,并告知客户端错误信息

**用例名称**
UpdateIsNotAllowedIfBoardWithSameNameIsAlreadyExists

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"id":"fooId1",
	"name":"board-name2"
}
```


**响应体**
```
{
	"timestamp":1473856897850,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"同名看板已经存在,请使用其它名称。",
	"path":"/someone/boards/fooId1",
	"code":30011
}
```


-------
### 场景 ###
用户根据ID获取board时,如果该board存在,则返回其信息

**用例名称**
shouldReturnBoardWhenBoardIsExist

**URL**
http://localhost:8007/someone/boards/fooId

**请求体**
```
{
	"id":"fooId1",
	"name":"board-name2"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:37.868000",
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
	"modificationTime":"2016-09-14 20:41:37.868000",
	"name":"board-name",
	"id":"fooId"
}
```


-------
### 场景 ###
当用户创建一个board时,如果存在同名,则不允许创建,并告知客户端错误信息

**用例名称**
NotAllowedIfBoardWithSameNameIsAlreadyExists

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"name":"board-name"
}
```


**响应体**
```
{
	"timestamp":1473856897950,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"同名看板已经存在,请使用其它名称。",
	"path":"/someone/boards",
	"code":30011
}
```


-------
### 场景 ###
获取指定用户所拥有的boards

**用例名称**
findByUserName_shouldReturnAllBoardsSuccessfully

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
[
	{
		"creationTime":"2016-09-14 20:41:37.969000",
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
		"modificationTime":"2016-09-14 20:41:37.969000",
		"name":"board-name",
		"id":"fooId"
	}
]
```


-------
### 场景 ###
当创建一个board时,如果参数合法,则创建成功并返回创建后的board

**用例名称**
shouldReturn201WhenCreateBoardSuccessfully

**URL**
http://localhost:8007/someone/boards

**请求体**
```
{
	"author":"someone",
	"id":"fooId",
	"name":"board-name"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:38.356000",
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
	"modificationTime":"2016-09-14 20:41:38.356000",
	"name":"board-name",
	"id":"fooId"
}
```


-------
### 场景 ###
当用户删除一个指定的board时,如果该board存在,则删除成功

**用例名称**
shouldDeleteSuccessfullyWhenTheBoardIsExist

**URL**
http://localhost:8007/someone/boards/fooId

**请求体**
```
{
	"author":"someone",
	"id":"fooId",
	"name":"board-name"
}
```


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
### 场景 ###
当看板不存在时,则不允许更新

**用例名称**
shouldUpdateFailedWhenTheBoardIsNotExist

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"id":"fooId",
	"name":"new-name"
}
```


**响应体**
```
{
	"timestamp":1473856898561,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"看板不存在。",
	"path":"/someone/boards/fooId",
	"code":30012
}
```


-------
### 场景 ###
当用户删除一个指定的board时,如果该board不存在,则返回客户端404错误

**用例名称**
shouldThrowResourceNotFoundExceptionWhenBoardToDeleteIsNotExist

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"id":"fooId",
	"name":"new-name"
}
```


**响应体**
```
{
	"timestamp":1473856898594,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"看板不存在。",
	"path":"/someone/boards/fooId",
	"code":30012
}
```


-------
### 场景 ###
当移动一个卡片时,移动后的顺序大于初始顺序

**用例名称**
update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumber

**URL**
http://localhost:8007/procedures/1/cards/fooId2

**请求体**
```
{
	"orderNumber":3,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-14 20:41:38.615000",
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
	"modificationTime":"2016-09-14 20:41:38.615000",
	"id":"fooId2",
	"procedureId":"1"
}
```


-------
### 场景 ###
当更新一个卡片时,如果待更新的卡片不存在,则抛出资源不存在的错误

**用例名称**
update_shouldThrowResourceNotFoundExceptionWhenCardToUpdateIsNotExist

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856898847,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[fooId] is not found.",
	"path":"/procedures/1/cards/fooId",
	"code":404
}
```


-------
### 场景 ###
当创建一个卡片时,如果卡片概述长度超过50,则创建失败

**用例名称**
create_shouldFailedIfSummaryIsTooLong

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"summary":"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限"
}
```


**响应体**
```
{
	"timestamp":1473856898933,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"卡片概述长度超限,请保持在50个字符以内。",
	"path":"/procedures/fooId/cards",
	"code":400
}
```


-------
### 场景 ###
根据ID查找一个卡片时,如果卡片存在,则返回该卡片

**用例名称**
findById_shouldReturnCardSuccessfully

**URL**
http://localhost:8007/procedures/1/cards/1

**请求体**
```
{
	"summary":"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限"
}
```


**响应体**
```
{
	"summary":"this is the card summary.",
	"orderNumber":0,
	"creationTime":"2016-09-14 20:41:38.965000",
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
	"modificationTime":"2016-09-14 20:41:38.965000",
	"id":"1",
	"procedureId":"1",
	"content":"play badminton"
}
```


-------
### 场景 ###
当一个卡片从某个procedure移动到另一个procedure时,不仅需要重新排序目标procedure,也要对原始procedure排序

**用例名称**
update_shouldResortSuccessfullyWhenCardIsFromAntherProcedure

**URL**
http://localhost:8007/procedures/1/cards/fooId6

**请求体**
```
{
	"orderNumber":3,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-14 20:41:39.062000",
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
	"modificationTime":"2016-09-14 20:41:39.062000",
	"id":"fooId6",
	"procedureId":"1"
}
```


-------
### 场景 ###
当移动一个卡片时,移动后的顺序小于其前置顺序

**用例名称**
update_shouldResortSuccessfullyWhenCurrentOrderNumberLessThanOriginNumber

**URL**
http://localhost:8007/procedures/1/cards/fooId4

**请求体**
```
{
	"orderNumber":1,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":1,
	"creationTime":"2016-09-14 20:41:39.158000",
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
	"modificationTime":"2016-09-14 20:41:39.158000",
	"id":"fooId4",
	"procedureId":"1"
}
```


-------
### 场景 ###
根据ID查找一个卡片时,如果卡片不存在,则抛出404的错误

**用例名称**
update_shouldFailedWhenCardIsNotExist

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"orderNumber":1,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856899281,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[feeId] is not found.",
	"path":"/procedures/fooId/cards/feeId",
	"code":404
}
```


-------
### 场景 ###
更新卡片成功

**用例名称**
update_shouldReturn200WhenUpdateCardSuccessfully

**URL**
http://localhost:8007/procedures/1/cards/fooId

**请求体**
```
{
	"orderNumber":3,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-14 20:41:39.317000",
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
	"modificationTime":"2016-09-14 20:41:39.317000",
	"id":"fooId",
	"procedureId":"1"
}
```


-------
### 场景 ###
当删除一个卡片时,如果待删除的卡片不存在,则抛出404错误

**用例名称**
delete_shouldDeleteFailedWhenTheCardIsNotExist

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"orderNumber":3,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856899453,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[non-exists-cardId] is not found.",
	"path":"/procedures/feeId/cards/non-exists-cardId",
	"code":404
}
```


-------
### 场景 ###
当根据procedureId查找其下属的卡片时,可以返回其所有卡片

**用例名称**
shouldReturnCardsWhenFindCardsByProcedureIdSuccessfully

**URL**
http://localhost:8007/procedures/fooId/cards

**请求体**
```
{
	"orderNumber":3,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
[
	{
		"summary":"this is the card summary.",
		"orderNumber":0,
		"creationTime":"2016-09-14 20:41:39.484000",
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
		"modificationTime":"2016-09-14 20:41:39.484000",
		"id":"1",
		"procedureId":"fooId",
		"content":"play badminton"
	}
]
```


-------
### 场景 ###
当创建一个卡片时,如果卡片所属的procedure并不存在,则创建失败

**用例名称**
create_shouldCreateFailedWhenProcedureIsNotFound

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"procedureId":"non-exists-procedureId",
	"summary":"summary"
}
```


**响应体**
```
{
	"timestamp":1473856899615,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[non-exists-procedureId] is not found.",
	"path":"/procedures/non-exists-procedureId/cards",
	"code":404
}
```


-------
### 场景 ###
当删除一个卡片时,如果卡片存在,则删除成功

**用例名称**
delete_shouldDeleteSuccessfullyWhenTheCardIsExist

**URL**
http://localhost:8007/procedures/feeId/cards/fooId

**请求体**
```
{
	"procedureId":"non-exists-procedureId",
	"summary":"summary"
}
```


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
### 场景 ###
创建一个新的卡片

**用例名称**
create_shouldReturn201WhenCreateCardSuccessfully

**URL**
http://localhost:8007/procedures/fooId/cards

**请求体**
```
{
	"id":"fooId",
	"procedureId":"fooId",
	"summary":"summary"
}
```


**响应体**
```
{
	"summary":"summary",
	"orderNumber":0,
	"creationTime":"2016-09-14 20:41:39.696000",
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
	"modificationTime":"2016-09-14 20:41:39.696000",
	"id":"fooId",
	"procedureId":"fooId"
}
```


-------
### 场景 ###
当创建一个卡片时,如果卡片概述为空,则创建失败

**用例名称**
create_shouldFailedIfSummaryIsNull

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"summary":""
}
```


**响应体**
```
{
	"timestamp":1473856899762,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"卡片概述不能为空。",
	"path":"/procedures/fooId/cards",
	"code":400
}
```


-------
### 场景 ###
当移动一个卡片时,卡片移动后的序号大于其前置序号,但在procedure中它移动后的序号并不是最大的。

**用例名称**
update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumberButNotTheBiggest

**URL**
http://localhost:8007/procedures/1/cards/fooId1

**请求体**
```
{
	"orderNumber":3,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"summary":"newSummary",
	"orderNumber":3,
	"creationTime":"2016-09-14 20:41:39.776000",
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
	"modificationTime":"2016-09-14 20:41:39.776000",
	"id":"fooId1",
	"procedureId":"1"
}
```


-------
### 场景 ###
当根据procedureID查找卡片时,如果procedure不存在,则抛出404异常

**用例名称**
findCardsByProcedureId_shouldReturn404WhenProcedureIsNotFound

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"orderNumber":3,
	"procedureId":"1",
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856899859,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[2] is not found.",
	"path":"/procedures/2/cards",
	"code":404
}
```


-------
### 场景 ###
创建一个新的卡片

**用例名称**
create_shouldReturn201WhenCreateCardSuccessfully

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"procedureId":"fooId",
	"summary":"summary"
}
```


**响应体**
```
{
	"timestamp":1473856899915,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[fooId] is not found.",
	"path":"/procedures/fooId/cards",
	"code":404
}
```


-------
### 场景 ###
当头部信息的userName和路径中的不一致时,告知客户端错误

**用例名称**
throwExceptionIfUserNameInHeaderIsNotEqualWithItInPath

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"name":"teamName"
}
```


**响应体**
```
{
	"timestamp":1473856899966,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"请求头部的用户名与URL中的不一致,请求可能被篡改。",
	"path":"/thief/teams",
	"code":-99
}
```


-------
### 场景 ###
当token不为空且未失效时,请求到达后更新token的有效期

**用例名称**
shouldUpdateTokenExpiredTime

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856904065,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"card[fooId] is not found.",
	"path":"/procedures/1/cards/fooId",
	"code":404
}
```


-------
### 场景 ###
如果用户在5分钟内未发送请求,token将会失效,告知客户端需要重新授权

**用例名称**
shouldReturnTimeOut

**URL**
http://localhost:8007/error/businessException

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856904120,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"认证已经过期,请重新认证获取token.",
	"path":"/resource",
	"code":1102
}
```


-------
### 场景 ###
当请求需要认证时,如果没有携带token,则告知客户端需要授权

**用例名称**
shouldReturn401WhenAuthIsRequired

**URL**
http://localhost:8007/error/businessException

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856904153,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"当前用户未认证,请先登录认证。",
	"path":"/resource",
	"code":1101
}
```


-------
### 场景 ###
当token中的用户名与header中携带的用户名不一致时,告知客户端认证未通过

**用例名称**
shouldAuthenticatedFailedWhenUserNameIsNotConsistent

**URL**
http://localhost:8007/error/businessException

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856904220,
	"status":500,
	"error":"Internal Server Error",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"请求头部的用户名与token中的不一致,请求可能被篡改。",
	"path":"/resource",
	"code":1103
}
```


-------
### 场景 ###
用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配未通过则登录失败

**用例名称**
login_shouldLoginFailedIfUserNameOrPasswordIsIncorrect

**URL**
http://localhost:8007/error/invalidParamsException?identity=someone&password=TvHB9NPWMvWtIxLADmKS4u42GemPPFEyyjBsoXnIEM31orSQF1Hm4gd5V9x5FohowfWj6pGvQCmXvNzh%2B2fHyMBrNrplsgoERZjag%2F%2FhnCPtH3Hszr8XgpVz6VS%2Fe%2BeA8yxj48owZ0MFjNZrnHy5KnlClAzk3sNy2eBy%2B%2FoKazg%3D

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856904366,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"用户名或密码错误。",
	"path":"/login",
	"code":400
}
```


-------
### 场景 ###
用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配通过则颁发token

**用例名称**
login_loginSuccessfully

**URL**
http://localhost:8007/login?identity=someone&password=L%2BzonciGtzdDXFUoIV3GfQEiCFQzz6H10YNX6QIpg5kPmsuegzM99eop7LOIt0w%2Ba7KjBzQJ5D9PFdXU4qDNO6VCOiEZxE2kx6pg9bJqHNUq%2FhRj9UXowrnhuxw106djrCeD8HE9z9OVQqmSndbX%2BY2b79wmJyY5tmdJQ%2Fj1MKQ%3D

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"_links":{
		"teams":{
			"href":"http://localhost:8007/someone/teams"
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
### 场景 ###
用户登录系统时,如果身份信息为空,则不允许登录并告知客户端错误信息

**用例名称**
login_loginFailed

**URL**
http://localhost:8007/error/invalidParamsException?password=foo

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856904588,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"登录失败,请输入您的用户名或邮箱。",
	"path":"/login",
	"code":400
}
```


-------
### 场景 ###
用户登录系统时,如果用户不存在,则不允许登录并告知客户端错误信息

**用例名称**
login_loginFailedIfRegUserIsNotExists

**URL**
http://localhost:8007/error/invalidParamsException?identity=foo&password=foo

**请求体**
```
{
	"summary":"newSummary"
}
```


**响应体**
```
{
	"timestamp":1473856904658,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"用户不存在,请先注册。",
	"path":"/login",
	"code":10011
}
```


-------
### 场景 ###
用户重置密码后，若再次重置，告知客户端请求无效

**用例名称**
ResetPasswordIsNotAllowedIfTheApplicationHasBeenAlreadyReset

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"password":"foo"
}
```


**响应体**
```
{
	"timestamp":1473856904766,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"未找到密码重置申请记录。",
	"path":"/tao/password",
	"code":20014
}
```


-------
### 场景 ###
验证码使用后若再次被使用，告示客户端验证码无效

**用例名称**
verificationCodeWillBeInvalidIfAlreadyBeingUsed

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"verificationCode":"000000"
}
```


**响应体**
```
{
	"timestamp":1473856904806,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"未找到密码找回申请记录,请核对你的邮箱或重新发送验证码。",
	"path":"/tao/passwordResetApplication",
	"code":20013
}
```


-------
### 场景 ###
用户取得验证码后，和邮箱一起发送到服务端验证，如果验证码正确且未过期，则发送密码重置的链接

**用例名称**
verifyVerificationCode

**URL**
http://localhost:8007/tao/passwordResetApplication

**请求体**
```
{
	"id":"fooId",
	"userName":"tao",
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
### 场景 ###
当用户请求找回密码时,需要提供邮箱,如果未提供则告知客户端错误

**用例名称**
NotAllowedIfEmailIsNotProvide

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	
}
```


**响应体**
```
{
	"timestamp":1473856904913,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"用于找回密码的邮箱不能为空.",
	"path":"/passwordRetrievalApplication",
	"code":400
}
```


-------
### 场景 ###
用户通过验证码验证,重置密码成功。

**用例名称**
resetPassword

**URL**
http://localhost:8007/tao/password

**请求体**
```
{
	"password":"OEiz+vEqM5OR5VPqMoWs2y8uBCy2/aMhoGdyWoTcOBGn6XB5+jeBNII5whGqRuKGYJK5tHHIX1cPwCktoW2ALOhW1dKV9LPa805GGZUNMWinOhwWjP1FYoA5d/cJhXBwA5/iHOeRDfnwnSwXoh4UMxwyhMzd8yS27FvHq7tD07A="
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
### 场景 ###
邮箱通过格式校验且存在后，创建密码找回申请记前,如果存在未完成的申请,则将其废弃

**用例名称**
discardingUnfinishedPasswordRetrievalApplication

**URL**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
	"email":"766191920@qq.com",
	"id":"fooId",
	"userName":"tao",
	"verificationCode":"000000"
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
### 场景 ###
验证码超过五分钟后,验证失败

**用例名称**
verificationCodeTimeOut

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"verificationCode":"000000"
}
```


**响应体**
```
{
	"timestamp":1473856906038,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"验证码已过期。",
	"path":"/tao/passwordResetApplication",
	"code":20012
}
```


-------
### 场景 ###
邮箱通过格式校验且存在后，发送找回密码的验证码到邮箱

**用例名称**
sendVerificationCode

**URL**
http://localhost:8007/passwordRetrievalApplication

**请求体**
```
{
	"email":"766191920@qq.com",
	"id":"fooId",
	"userName":"tao",
	"verificationCode":"000000"
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
### 场景 ###
当用户请求找回密码时,需要提供邮箱,如果邮箱不存在则告知客户端错误

**用例名称**
NotAllowedIfEmailFormatIsNotExists

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"email":"email@email.com"
}
```


**响应体**
```
{
	"timestamp":1473856906853,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邮箱不存在。",
	"path":"/passwordRetrievalApplication",
	"code":20011
}
```


-------
### 场景 ###
当用户请求找回密码时,需要提供邮箱,如果邮箱格式错误则告知客户端错误

**用例名称**
NotAllowedIfEmailFormatIsNotCorrect

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"email":"email"
}
```


**响应体**
```
{
	"timestamp":1473856906882,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"邮箱格式错误.",
	"path":"/passwordRetrievalApplication",
	"code":400
}
```


-------
### 场景 ###
验证码错误,验证失败

**用例名称**
VerificationWillBeFailedIfVerificationCodeIsNotCorrect

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"verificationCode":"000001"
}
```


**响应体**
```
{
	"timestamp":1473856906913,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"验证码错误。",
	"path":"/tao/passwordResetApplication",
	"code":20015
}
```


-------
### 场景 ###
创建一个新的procedure,如果它并不是指定boardId下第一个procedure,则其排序号应根据当前procedure数量自动增加

**用例名称**
create_orderNumberShouldAutoIncrease

**URL**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"id":"fooId",
	"title":"title."
}
```


**响应体**
```
{
	"orderNumber":1,
	"creationTime":"2016-09-14 20:41:46.961000",
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
	"modificationTime":"2016-09-14 20:41:46.961000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"title."
}
```


-------
### 场景 ###
更新procedure时,如果参数合法且待更新的procedure存在,则更新成功

**用例名称**
shouldUpdateSuccessfully

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**请求体**
```
{
	"id":"fooId",
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
{
	"orderNumber":0,
	"creationTime":"2016-09-14 20:41:47.028000",
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
	"modificationTime":"2016-09-14 20:41:47.028000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"newTitle"
}
```


-------
### 场景 ###
当移动一个procedure时,移动后的排序小于其原先的排序

**用例名称**
update_shouldResortSuccessfullyWhenCurrentSortNumberIsLessThanOriginNumber

**URL**
http://localhost:8007/boards/feeId/procedures/fooId2

**请求体**
```
{
	"id":"fooId2",
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
{
	"orderNumber":0,
	"creationTime":"2016-09-14 20:41:47.081000",
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
	"modificationTime":"2016-09-14 20:41:47.081000",
	"boardId":"feeId",
	"id":"fooId2",
	"title":"newTitle"
}
```


-------
### 场景 ###
当根据procedureId查找procedure时,如果procedure存在,则将其返回

**用例名称**
shouldReturnProcedureWhenFindProcedureById

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**请求体**
```
{
	"id":"fooId2",
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
{
	"orderNumber":0,
	"creationTime":"2016-09-14 20:41:47.147000",
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
	"modificationTime":"2016-09-14 20:41:47.147000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"this is the first procedure."
}
```


-------
### 场景 ###
创建新的procedure时,如果名称为空,则不允许创建并返回客户端400错误

**用例名称**
shouldFailedIfProcedureTitleIsEmpty

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"title":""
}
```


**响应体**
```
{
	"timestamp":1473856907200,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"工序名称不能为空。",
	"path":"/boards/feeId/procedures",
	"code":400
}
```


-------
### 场景 ###
更新procedure时,如果参数合法但待更新的procedure不存在,则更新失败

**用例名称**
update_shouldFailedWhenTheProcedureToUpdateIsNotExists

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"id":"fooId",
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
{
	"timestamp":1473856907236,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[fooId] is not found.",
	"path":"/boards/feeId/procedures/fooId",
	"code":404
}
```


-------
### 场景 ###
通过boardId获取所有的procedure

**用例名称**
shouldReturnAllEntriesSuccessfully

**URL**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"id":"fooId",
	"orderNumber":0,
	"title":"newTitle"
}
```


**响应体**
```
[
	{
		"orderNumber":0,
		"creationTime":"2016-09-14 20:41:47.249000",
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
		"modificationTime":"2016-09-14 20:41:47.249000",
		"boardId":"feeId",
		"id":"fooId",
		"title":"this is the first procedure."
	}
]
```


-------
### 场景 ###
创建新的procedure时,如果名称长度超限,则不允许创建并返回客户端400错误

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsTooLong

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"title":"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限"
}
```


**响应体**
```
{
	"timestamp":1473856907320,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"工序名称长度超限,请保持在30个字符以内。",
	"path":"/boards/feeId/procedures",
	"code":400
}
```


-------
### 场景 ###
当删除一个procedure时,如果待删除的procedure存在,则删除成功

**用例名称**
shouldDeleteSuccessfullyWhenTheProcedureIsExist

**URL**
http://localhost:8007/boards/feeId/procedures/fooId

**请求体**
```
{
	"title":"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限"
}
```


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
### 场景 ###
创建新的procedure时,如果名称为空字符串,则不允许创建并返回客户端400错误

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsEmpty

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"title":""
}
```


**响应体**
```
{
	"timestamp":1473856907409,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"工序名称不能为空。",
	"path":"/boards/feeId/procedures",
	"code":400
}
```


-------
### 场景 ###
当删除一个procedure时,如果待删除的procedure不存在,则删除成功并返回客户端错误

**用例名称**
shouldThrowResourceNotFoundExceptionWhenProcedureToDeleteIsNotExist

**URL**
http://localhost:8007/error/404

**请求体**
```
{
	"title":""
}
```


**响应体**
```
{
	"timestamp":1473856907437,
	"status":404,
	"error":"Not Found",
	"exception":"org.thiki.kanban.foundation.exception.ResourceNotFoundException",
	"message":"procedure[fooId] is not found.",
	"path":"/boards/feeId/procedures/fooId",
	"code":404
}
```


-------
### 场景 ###
当移动一个procedure时,移动后的排序大于其原先的排序

**用例名称**
update_shouldResortSuccessfullyWhenCurrentSortNumberIsMoreThanOriginNumber

**URL**
http://localhost:8007/boards/feeId/procedures/fooId1

**请求体**
```
{
	"id":"fooId1",
	"orderNumber":2,
	"title":"newTitle"
}
```


**响应体**
```
{
	"orderNumber":2,
	"creationTime":"2016-09-14 20:41:47.460000",
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
	"modificationTime":"2016-09-14 20:41:47.460000",
	"boardId":"feeId",
	"id":"fooId1",
	"title":"newTitle"
}
```


-------
### 场景 ###
创建新的procedure时,同一看板下已经存在同名,则不允许创建并返回客户端400错误

**用例名称**
shouldReturnBadRequestWhenProcedureTitleIsAlreadyExits

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"title":"procedure"
}
```


**响应体**
```
{
	"timestamp":1473856907528,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"该名称已经被使用,请使用其它名称。",
	"path":"/boards/feeId/procedures",
	"code":30011
}
```


-------
### 场景 ###
创建一个新的procedure后,返回自身及links信息

**用例名称**
shouldReturn201WhenCreateProcedureSuccessfully

**URL**
http://localhost:8007/boards/feeId/procedures

**请求体**
```
{
	"id":"fooId",
	"title":"this is the procedure title."
}
```


**响应体**
```
{
	"orderNumber":0,
	"creationTime":"2016-09-14 20:41:47.569000",
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
	"modificationTime":"2016-09-14 20:41:47.569000",
	"boardId":"feeId",
	"id":"fooId",
	"title":"this is the procedure title."
}
```


-------
### 场景 ###
当用户请求登录或注册时,首先需要向系统发送一次认证请求,将公钥发送至客户端

**用例名称**
identification_askForAuthenticationWhenUserIsExists

**URL**
http://localhost:8007/publicKey

**请求体**
```
{
	"id":"fooId",
	"title":"this is the procedure title."
}
```


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
### 场景 ###
用户注册时,如果用户名已经存在,则不允许注册

**用例名称**
registerNewUser_shouldRejectWithConflictWhenUserNameExists

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"email":"someone@gmail.com",
	"name":"someone",
	"password":"fee"
}
```


**响应体**
```
{
	"timestamp":1473856907721,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"用户名已经被使用,请使用其他用户名。",
	"path":"/registration",
	"code":10011
}
```


-------
### 场景 ###
用户注册时,用户名和邮箱在系统中都不存在,但是密码未通过公钥加密,则不允许注册

**用例名称**
registerNewUser_shouldFailIfPasswordIsNotEncryptedWithPublicKey

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"email":"someone@gmail.com",
	"name":"someone",
	"password":"foo",
	"salt":"fooId"
}
```


**响应体**
```
{
	"timestamp":1473856907788,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"通过私钥解密失败,请确保数据已经通过公钥加密。",
	"path":"/registration",
	"code":400
}
```


-------
### 场景 ###
用户注册时,如果邮箱已经存在,则不允许注册

**用例名称**
registerNewUser_shouldRejectWithConflictWhenUserEmailExists

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"email":"someone@gmail.com",
	"name":"someoneElse",
	"password":"fee"
}
```


**响应体**
```
{
	"timestamp":1473856907841,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邮箱已经存在,请使用其他邮箱。",
	"path":"/registration",
	"code":10012
}
```


-------
### 场景 ###
用户注册时,根据服务端提供的公钥对密码进行加密,服务端拿到加密的密码后,首选用私钥解密,再通过MD5算法加盐加密

**用例名称**
registerNewUser_shouldReturn201WhenRegisterSuccessfully

**URL**
http://localhost:8007/registration

**请求体**
```
{
	"email":"someone@gmail.com",
	"id":"fooId",
	"name":"someone",
	"password":"148412d9df986f739038ad22c77459f2",
	"salt":"fooId"
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
### 场景 ###
加入团队时,如果待加入的成员已经在团队中,则不允许加入

**用例名称**
joinTeam_shouldReturnFailedIfMemberIsAlreadyIn

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"member":"someone"
}
```


**响应体**
```
{
	"timestamp":1473856907947,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"Member named someone is already in the teams.",
	"path":"/teams/foo-teamId/teamMembers",
	"code":400
}
```


-------
### 场景 ###
当用户加入一个团队后，可以获取该团队的所有成员

**用例名称**
loadTeamMembersByTeamId

**URL**
http://localhost:8007/teams/foo-teamId/members

**请求体**
```
{
	"member":"someone"
}
```


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
### 场景 ###
加入团队时,如果该团队并不存在,则不允许加入

**用例名称**
joinTeam_shouldReturnFailedIfTeamIsNotExist

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"member":"someone"
}
```


**响应体**
```
{
	"timestamp":1473856908055,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"Team foo-teamId is not found.",
	"path":"/teams/foo-teamId/teamMembers",
	"code":400
}
```


-------
### 场景 ###
若当前用户并非团队成员，则不允许获取

**用例名称**
NotAllowedIfCurrentUserIsNotAMemberOfTheTeamWhenLoadingTeamMembersByTeamId

**URL**
http://localhost:8007/error/businessException

**请求体**
```
{
	"member":"someone"
}
```


**响应体**
```
{
	"timestamp":1473856908099,
	"status":401,
	"error":"Unauthorized",
	"exception":"org.thiki.kanban.foundation.exception.UnauthorisedException",
	"message":"当前用户非该团队成员,允许获取。",
	"path":"/teams/foo-teamId/members",
	"code":60011
}
```


-------
### 场景 ###
加入一个团队

**用例名称**
joinTeam_shouldReturn201WhenJoinTeamSuccessfully

**URL**
http://localhost:8007/teams/foo-teamId/teamMembers

**请求体**
```
{
	"author":"someone",
	"id":"fooId",
	"member":"someone",
	"teamId":"foo-teamId"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:48.143000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-teamId/teamMembers"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-14 20:41:48.143000",
	"teamId":"foo-teamId",
	"member":"someone",
	"id":"fooId"
}
```


-------
### 场景 ###
当用户加入一个团队后，可以获取该团队的所有成员。但是当团队不存在时,则不允许获取。

**用例名称**
NotAllowedIfTeamIsNotExitsWhenLoadingTeamMembersByTeamId

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"author":"someone",
	"id":"fooId",
	"member":"someone",
	"teamId":"foo-teamId"
}
```


**响应体**
```
{
	"timestamp":1473856908191,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/foo-teamId/members",
	"code":50012
}
```


-------
### 场景 ###
如果邀请人为空，怎不允许发送邀请

**用例名称**
NotAllowedIfInviteeIsEmpty

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"invitee":""
}
```


**响应体**
```
{
	"timestamp":1473856908237,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"请指定被邀请的成员。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":400
}
```


-------
### 场景 ###
用户可以通过用户名邀请其他成员加入到团队中

**用例名称**
inviteOthersWithEmailToJoinTeam

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"id":"fooId",
	"invitee":"invitee-user",
	"inviter":"someone",
	"teamId":"foo-team-Id"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:48.308000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-14 20:41:48.308000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
### 场景 ###
如果被邀请人已经是团队的成员，则不允许发送邀请

**用例名称**
NotAllowedIfInviteeIsAlreadyAMemberOfTheTeam

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1473856909140,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请对象已经是该团队成员,无须再次邀请。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70014
}
```


-------
### 场景 ###
如果此前已经存在相同的邀请，则取消之前的邀请

**用例名称**
cancelPreviousInvitationBeforeSendingNewInvitation

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"id":"fooId",
	"invitee":"invitee-user",
	"inviter":"someone",
	"teamId":"foo-team-Id"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:49.186000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-14 20:41:49.186000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
### 场景 ###
用户可以通过用户名邀请其他成员加入到团队中

**用例名称**
inviteOthersWithUserNameToJoinTeam

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"id":"fooId",
	"invitee":"invitee-user",
	"inviter":"someone",
	"teamId":"foo-team-Id"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:50.046000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-14 20:41:50.046000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
### 场景 ###
如果被邀请人不存在，则不允许发送邀请

**用例名称**
NotAllowedIfInviteeIsNotExist

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1473856910806,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"被邀请的成员不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70012
}
```


-------
### 场景 ###
如果邀请人并非团队的成员则不允许发送邀请

**用例名称**
NotAllowedIfInviterIsNotAMemberOfTheTeam

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1473856910860,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请人并不是当前团队的成员,不允许邀请他人进入该团队。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70013
}
```


-------
### 场景 ###
邀请发出后，用户的消息中心也会收到相应的提示

**用例名称**
addNotificationAfterSendingInvitation

**URL**
http://localhost:8007/teams/foo-team-Id/members/invitation

**请求体**
```
{
	"id":"fooId",
	"invitee":"invitee-user",
	"inviter":"someone",
	"teamId":"foo-team-Id"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:50.924000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-14 20:41:50.924000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
### 场景 ###
如果邀请加入的团队并不存在，则不允许发送邀请

**用例名称**
NotAllowedIfTeamIsNotExist

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"timestamp":1473856911786,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70011
}
```


-------
### 场景 ###
创建团队时，如果团队名称为空，则不允许创建

**用例名称**
creationIsNotAllowedIfTeamNameIsEmpty

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"name":""
}
```


**响应体**
```
{
	"timestamp":1473856911834,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"团队名称不可以为空。",
	"path":"/someone/teams",
	"code":400
}
```


-------
### 场景 ###
用户根据ID获取team时,如果该team存在,则返回其信息

**用例名称**
shouldReturnBoardWhenTeamIsExist

**URL**
http://localhost:8007/teams/fooId

**请求体**
```
{
	"name":""
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:51.857000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-14 20:41:51.857000",
	"name":"team-name",
	"id":"fooId"
}
```


-------
### 场景 ###
创建团队时，如果团队名称超限，则不允许创建

**用例名称**
creationIsNotAllowedIfTeamNameIsTooLong

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"name":"团队名称团队名称团队名称团队名称团队名称团队名称团队名称"
}
```


**响应体**
```
{
	"timestamp":1473856911928,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"看板名称过长,请保持在10个字以内。",
	"path":"/someone/teams",
	"code":400
}
```


-------
### 场景 ###
根据用户名获取其所在团队

**用例名称**
loadTheTeamsWhichTheUserIsIn

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
[
	{
		"creationTime":"2016-09-14 20:41:51.984000",
		"_links":{
			"members":{
				"href":"http://localhost:8007/teams/fooId/members"
			},
			"self":{
				"href":"http://localhost:8007/teams/fooId"
			}
		},
		"author":"someone",
		"modificationTime":"2016-09-14 20:41:51.984000",
		"name":"team-name",
		"id":"fooId"
	}
]
```


-------
### 场景 ###
创建一个团队

**用例名称**
create_shouldReturn201WhenCreateTeamSuccessfully

**URL**
http://localhost:8007/someone/teams

**请求体**
```
{
	"id":"fooId",
	"name":"思奇团队讨论组"
}
```


**响应体**
```
{
	"creationTime":"2016-09-14 20:41:52.045000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-14 20:41:52.045000",
	"name":"思奇团队讨论组",
	"id":"fooId"
}
```


-------
### 场景 ###
创建团队时，如果在本人名下已经存在相同名称的团队，则不允许创建

**用例名称**
creationIsNotAllowedIfTeamNameIsConflict

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	"name":"team-name"
}
```


**响应体**
```
{
	"timestamp":1473856912102,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"同名团队已经存在。",
	"path":"/someone/teams",
	"code":50011
}
```


-------
### 场景 ###
创建团队时，如果未提供团队名称，则不允许创建

**用例名称**
creationIsNotAllowedIfTeamNameIsNull

**URL**
http://localhost:8007/error/invalidParamsException

**请求体**
```
{
	
}
```


**响应体**
```
{
	"timestamp":1473856912145,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"团队名称不可以为空。",
	"path":"/someone/teams",
	"code":400
}
```


-------