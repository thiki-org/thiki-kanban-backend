
# 五、团队 #

## 创建团队时，如果团队名称为空，则不允许创建 ##

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
	"timestamp":1473906497703,
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

**请求体**
```
{
	"name":""
}
```


**响应体**
```
{
	"creationTime":"2016-09-15 10:28:17.715000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-15 10:28:17.715000",
	"name":"team-name",
	"id":"fooId"
}
```


-------
## 创建团队时，如果团队名称超限，则不允许创建 ##

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
	"timestamp":1473906497796,
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
		"creationTime":"2016-09-15 10:28:17.811000",
		"_links":{
			"members":{
				"href":"http://localhost:8007/teams/fooId/members"
			},
			"self":{
				"href":"http://localhost:8007/teams/fooId"
			}
		},
		"author":"someone",
		"modificationTime":"2016-09-15 10:28:17.811000",
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
	"id":"fooId",
	"name":"思奇团队讨论组"
}
```


**响应体**
```
{
	"creationTime":"2016-09-15 10:28:17.921000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-15 10:28:17.921000",
	"name":"思奇团队讨论组",
	"id":"fooId"
}
```


-------
## 创建团队时，如果在本人名下已经存在相同名称的团队，则不允许创建 ##

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
	"timestamp":1473906497986,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"同名团队已经存在。",
	"path":"/someone/teams",
	"code":50011
}
```


-------
## 创建团队时，如果未提供团队名称，则不允许创建 ##

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
	"timestamp":1473906498025,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"团队名称不可以为空。",
	"path":"/someone/teams",
	"code":400
}
```


-------
# 七、团队加入邀请 #

## 如果邀请人为空，怎不允许发送邀请 ##

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
	"timestamp":1473906489963,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.InvalidParamsException",
	"message":"请指定被邀请的成员。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":400
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
	"id":"fooId",
	"invitee":"invitee-user",
	"inviter":"someone",
	"teamId":"foo-team-Id"
}
```


**响应体**
```
{
	"creationTime":"2016-09-15 10:28:10.639000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-15 10:28:10.639000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
## 如果被邀请人已经是团队的成员，则不允许发送邀请 ##

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
	"timestamp":1473906493010,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请对象已经是该团队成员,无须再次邀请。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70014
}
```


-------
## 如果此前已经存在相同的邀请，则取消之前的邀请 ##

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
	"creationTime":"2016-09-15 10:28:13.089000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-15 10:28:13.089000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
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
	"id":"fooId",
	"invitee":"invitee-user",
	"inviter":"someone",
	"teamId":"foo-team-Id"
}
```


**响应体**
```
{
	"creationTime":"2016-09-15 10:28:14.522000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-15 10:28:14.522000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
## 如果被邀请人不存在，则不允许发送邀请 ##

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
	"timestamp":1473906496051,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"被邀请的成员不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70012
}
```


-------
## 如果邀请人并非团队的成员则不允许发送邀请 ##

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
	"timestamp":1473906496121,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请人并不是当前团队的成员,不允许邀请他人进入该团队。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70013
}
```


-------
## 邀请发出后，用户的消息中心也会收到相应的提示 ##

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
	"creationTime":"2016-09-15 10:28:16.192000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/foo-team-Id/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation"
		}
	},
	"modificationTime":"2016-09-15 10:28:16.192000",
	"teamId":"foo-team-Id",
	"inviter":"someone",
	"id":"fooId",
	"invitee":"invitee-user"
}
```


-------
## 如果邀请加入的团队并不存在，则不允许发送邀请 ##

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
	"timestamp":1473906497658,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"团队不存在。",
	"path":"/teams/foo-team-Id/members/invitation",
	"code":70011
}
```


-------