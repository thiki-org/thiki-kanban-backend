<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
#API概要文档#

- [零、入口](#%E9%9B%B6%E3%80%81%E5%85%A5%E5%8F%A3)
  - [初次访问系统时入口](#%E5%88%9D%E6%AC%A1%E8%AE%BF%E9%97%AE%E7%B3%BB%E7%BB%9F%E6%97%B6%E5%85%A5%E5%8F%A3)
- [一、注册](#%E4%B8%80%E3%80%81%E6%B3%A8%E5%86%8C)
  - [不允许注册>如果用户名已经存在,则不允许注册](#%E4%B8%8D%E5%85%81%E8%AE%B8%E6%B3%A8%E5%86%8C%E5%A6%82%E6%9E%9C%E7%94%A8%E6%88%B7%E5%90%8D%E5%B7%B2%E7%BB%8F%E5%AD%98%E5%9C%A8%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E6%B3%A8%E5%86%8C)
  - [不允许注册>用户名和邮箱在系统中都不存在,但是密码未通过公钥加密,则不允许注册](#%E4%B8%8D%E5%85%81%E8%AE%B8%E6%B3%A8%E5%86%8C%E7%94%A8%E6%88%B7%E5%90%8D%E5%92%8C%E9%82%AE%E7%AE%B1%E5%9C%A8%E7%B3%BB%E7%BB%9F%E4%B8%AD%E9%83%BD%E4%B8%8D%E5%AD%98%E5%9C%A8%E4%BD%86%E6%98%AF%E5%AF%86%E7%A0%81%E6%9C%AA%E9%80%9A%E8%BF%87%E5%85%AC%E9%92%A5%E5%8A%A0%E5%AF%86%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E6%B3%A8%E5%86%8C)
  - [不允许注册>如果邮箱已经存在,则不允许注册](#%E4%B8%8D%E5%85%81%E8%AE%B8%E6%B3%A8%E5%86%8C%E5%A6%82%E6%9E%9C%E9%82%AE%E7%AE%B1%E5%B7%B2%E7%BB%8F%E5%AD%98%E5%9C%A8%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E6%B3%A8%E5%86%8C)
  - [注册成功>根据服务端提供的公钥对密码进行加密,服务端拿到加密的密码后,首先用私钥解密,再通过MD5算法加盐加密](#%E6%B3%A8%E5%86%8C%E6%88%90%E5%8A%9F%E6%A0%B9%E6%8D%AE%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8F%90%E4%BE%9B%E7%9A%84%E5%85%AC%E9%92%A5%E5%AF%B9%E5%AF%86%E7%A0%81%E8%BF%9B%E8%A1%8C%E5%8A%A0%E5%AF%86%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8B%BF%E5%88%B0%E5%8A%A0%E5%AF%86%E7%9A%84%E5%AF%86%E7%A0%81%E5%90%8E%E9%A6%96%E5%85%88%E7%94%A8%E7%A7%81%E9%92%A5%E8%A7%A3%E5%AF%86%E5%86%8D%E9%80%9A%E8%BF%87md5%E7%AE%97%E6%B3%95%E5%8A%A0%E7%9B%90%E5%8A%A0%E5%AF%86)
- [二、登录](#%E4%BA%8C%E3%80%81%E7%99%BB%E5%BD%95)
  - [密码错误>用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配未通过则登录失败](#%E5%AF%86%E7%A0%81%E9%94%99%E8%AF%AF%E7%94%A8%E6%88%B7%E6%90%BA%E5%B8%A6%E9%80%9A%E8%BF%87%E5%85%AC%E9%92%A5%E5%8A%A0%E5%AF%86%E7%9A%84%E5%AF%86%E7%A0%81%E7%99%BB%E5%BD%95%E7%B3%BB%E7%BB%9F%E6%97%B6%E7%B3%BB%E7%BB%9F%E9%80%9A%E8%BF%87%E7%A7%81%E9%92%A5%E5%AF%B9%E5%85%B6%E8%A7%A3%E5%AF%86%E8%A7%A3%E5%AF%86%E5%90%8E%E5%86%8D%E9%80%9A%E8%BF%87md5%E5%8A%A0%E5%AF%86%E4%B8%8E%E6%95%B0%E6%8D%AE%E5%BA%93%E7%8E%B0%E6%9C%89%E7%B3%BB%E7%BB%9F%E5%8C%B9%E9%85%8D%E5%A6%82%E6%9E%9C%E5%8C%B9%E9%85%8D%E6%9C%AA%E9%80%9A%E8%BF%87%E5%88%99%E7%99%BB%E5%BD%95%E5%A4%B1%E8%B4%A5)
  - [登录成功>用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配通过则颁发token](#%E7%99%BB%E5%BD%95%E6%88%90%E5%8A%9F%E7%94%A8%E6%88%B7%E6%90%BA%E5%B8%A6%E9%80%9A%E8%BF%87%E5%85%AC%E9%92%A5%E5%8A%A0%E5%AF%86%E7%9A%84%E5%AF%86%E7%A0%81%E7%99%BB%E5%BD%95%E7%B3%BB%E7%BB%9F%E6%97%B6%E7%B3%BB%E7%BB%9F%E9%80%9A%E8%BF%87%E7%A7%81%E9%92%A5%E5%AF%B9%E5%85%B6%E8%A7%A3%E5%AF%86%E8%A7%A3%E5%AF%86%E5%90%8E%E5%86%8D%E9%80%9A%E8%BF%87md5%E5%8A%A0%E5%AF%86%E4%B8%8E%E6%95%B0%E6%8D%AE%E5%BA%93%E7%8E%B0%E6%9C%89%E7%B3%BB%E7%BB%9F%E5%8C%B9%E9%85%8D%E5%A6%82%E6%9E%9C%E5%8C%B9%E9%85%8D%E9%80%9A%E8%BF%87%E5%88%99%E9%A2%81%E5%8F%91token)
  - [登录信息不完整>用户登录系统时,如果身份信息为空,则不允许登录并告知客户端错误信息](#%E7%99%BB%E5%BD%95%E4%BF%A1%E6%81%AF%E4%B8%8D%E5%AE%8C%E6%95%B4%E7%94%A8%E6%88%B7%E7%99%BB%E5%BD%95%E7%B3%BB%E7%BB%9F%E6%97%B6%E5%A6%82%E6%9E%9C%E8%BA%AB%E4%BB%BD%E4%BF%A1%E6%81%AF%E4%B8%BA%E7%A9%BA%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E7%99%BB%E5%BD%95%E5%B9%B6%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF%E4%BF%A1%E6%81%AF)
  - [用户不存在>用户登录系统时,如果用户不存在,则不允许登录并告知客户端错误信息](#%E7%94%A8%E6%88%B7%E4%B8%8D%E5%AD%98%E5%9C%A8%E7%94%A8%E6%88%B7%E7%99%BB%E5%BD%95%E7%B3%BB%E7%BB%9F%E6%97%B6%E5%A6%82%E6%9E%9C%E7%94%A8%E6%88%B7%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E7%99%BB%E5%BD%95%E5%B9%B6%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF%E4%BF%A1%E6%81%AF)
- [三、看板](#%E4%B8%89%E3%80%81%E7%9C%8B%E6%9D%BF)
  - [成功更新一个board信息](#%E6%88%90%E5%8A%9F%E6%9B%B4%E6%96%B0%E4%B8%80%E4%B8%AAboard%E4%BF%A1%E6%81%AF)
  - [当更新一个board时,如果存在同名,则不允许更新,并告知客户端错误信息](#%E5%BD%93%E6%9B%B4%E6%96%B0%E4%B8%80%E4%B8%AAboard%E6%97%B6%E5%A6%82%E6%9E%9C%E5%AD%98%E5%9C%A8%E5%90%8C%E5%90%8D%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E6%9B%B4%E6%96%B0%E5%B9%B6%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF%E4%BF%A1%E6%81%AF)
  - [用户根据ID获取board时,如果该board存在,则返回其信息](#%E7%94%A8%E6%88%B7%E6%A0%B9%E6%8D%AEid%E8%8E%B7%E5%8F%96board%E6%97%B6%E5%A6%82%E6%9E%9C%E8%AF%A5board%E5%AD%98%E5%9C%A8%E5%88%99%E8%BF%94%E5%9B%9E%E5%85%B6%E4%BF%A1%E6%81%AF)
  - [当用户创建一个board时,如果存在同名,则不允许创建,并告知客户端错误信息](#%E5%BD%93%E7%94%A8%E6%88%B7%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AAboard%E6%97%B6%E5%A6%82%E6%9E%9C%E5%AD%98%E5%9C%A8%E5%90%8C%E5%90%8D%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA%E5%B9%B6%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF%E4%BF%A1%E6%81%AF)
  - [获取指定用户所拥有的boards](#%E8%8E%B7%E5%8F%96%E6%8C%87%E5%AE%9A%E7%94%A8%E6%88%B7%E6%89%80%E6%8B%A5%E6%9C%89%E7%9A%84boards)
  - [当创建一个board时,如果参数合法,则创建成功并返回创建后的board](#%E5%BD%93%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AAboard%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8F%82%E6%95%B0%E5%90%88%E6%B3%95%E5%88%99%E5%88%9B%E5%BB%BA%E6%88%90%E5%8A%9F%E5%B9%B6%E8%BF%94%E5%9B%9E%E5%88%9B%E5%BB%BA%E5%90%8E%E7%9A%84board)
  - [当用户删除一个指定的board时,如果该board存在,则删除成功](#%E5%BD%93%E7%94%A8%E6%88%B7%E5%88%A0%E9%99%A4%E4%B8%80%E4%B8%AA%E6%8C%87%E5%AE%9A%E7%9A%84board%E6%97%B6%E5%A6%82%E6%9E%9C%E8%AF%A5board%E5%AD%98%E5%9C%A8%E5%88%99%E5%88%A0%E9%99%A4%E6%88%90%E5%8A%9F)
  - [当看板不存在时,则不允许更新](#%E5%BD%93%E7%9C%8B%E6%9D%BF%E4%B8%8D%E5%AD%98%E5%9C%A8%E6%97%B6%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E6%9B%B4%E6%96%B0)
  - [当用户删除一个指定的board时,如果该board不存在,则返回客户端404错误](#%E5%BD%93%E7%94%A8%E6%88%B7%E5%88%A0%E9%99%A4%E4%B8%80%E4%B8%AA%E6%8C%87%E5%AE%9A%E7%9A%84board%E6%97%B6%E5%A6%82%E6%9E%9C%E8%AF%A5board%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E8%BF%94%E5%9B%9E%E5%AE%A2%E6%88%B7%E7%AB%AF404%E9%94%99%E8%AF%AF)
- [四、工序](#%E5%9B%9B%E3%80%81%E5%B7%A5%E5%BA%8F)
  - [创建一个新的procedure,如果它并不是指定boardId下第一个procedure,则其排序号应根据当前procedure数量自动增加](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E6%96%B0%E7%9A%84procedure%E5%A6%82%E6%9E%9C%E5%AE%83%E5%B9%B6%E4%B8%8D%E6%98%AF%E6%8C%87%E5%AE%9Aboardid%E4%B8%8B%E7%AC%AC%E4%B8%80%E4%B8%AAprocedure%E5%88%99%E5%85%B6%E6%8E%92%E5%BA%8F%E5%8F%B7%E5%BA%94%E6%A0%B9%E6%8D%AE%E5%BD%93%E5%89%8Dprocedure%E6%95%B0%E9%87%8F%E8%87%AA%E5%8A%A8%E5%A2%9E%E5%8A%A0)
  - [更新procedure时,如果参数合法且待更新的procedure存在,则更新成功](#%E6%9B%B4%E6%96%B0procedure%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8F%82%E6%95%B0%E5%90%88%E6%B3%95%E4%B8%94%E5%BE%85%E6%9B%B4%E6%96%B0%E7%9A%84procedure%E5%AD%98%E5%9C%A8%E5%88%99%E6%9B%B4%E6%96%B0%E6%88%90%E5%8A%9F)
  - [当移动一个procedure时,移动后的排序小于其原先的排序](#%E5%BD%93%E7%A7%BB%E5%8A%A8%E4%B8%80%E4%B8%AAprocedure%E6%97%B6%E7%A7%BB%E5%8A%A8%E5%90%8E%E7%9A%84%E6%8E%92%E5%BA%8F%E5%B0%8F%E4%BA%8E%E5%85%B6%E5%8E%9F%E5%85%88%E7%9A%84%E6%8E%92%E5%BA%8F)
  - [当根据procedureId查找procedure时,如果procedure存在,则将其返回](#%E5%BD%93%E6%A0%B9%E6%8D%AEprocedureid%E6%9F%A5%E6%89%BEprocedure%E6%97%B6%E5%A6%82%E6%9E%9Cprocedure%E5%AD%98%E5%9C%A8%E5%88%99%E5%B0%86%E5%85%B6%E8%BF%94%E5%9B%9E)
  - [创建新的procedure时,如果名称为空,则不允许创建并返回客户端400错误](#%E5%88%9B%E5%BB%BA%E6%96%B0%E7%9A%84procedure%E6%97%B6%E5%A6%82%E6%9E%9C%E5%90%8D%E7%A7%B0%E4%B8%BA%E7%A9%BA%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA%E5%B9%B6%E8%BF%94%E5%9B%9E%E5%AE%A2%E6%88%B7%E7%AB%AF400%E9%94%99%E8%AF%AF)
  - [更新procedure时,如果参数合法但待更新的procedure不存在,则更新失败](#%E6%9B%B4%E6%96%B0procedure%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8F%82%E6%95%B0%E5%90%88%E6%B3%95%E4%BD%86%E5%BE%85%E6%9B%B4%E6%96%B0%E7%9A%84procedure%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E6%9B%B4%E6%96%B0%E5%A4%B1%E8%B4%A5)
  - [通过boardId获取所有的procedure](#%E9%80%9A%E8%BF%87boardid%E8%8E%B7%E5%8F%96%E6%89%80%E6%9C%89%E7%9A%84procedure)
  - [创建新的procedure时,如果名称长度超限,则不允许创建并返回客户端400错误](#%E5%88%9B%E5%BB%BA%E6%96%B0%E7%9A%84procedure%E6%97%B6%E5%A6%82%E6%9E%9C%E5%90%8D%E7%A7%B0%E9%95%BF%E5%BA%A6%E8%B6%85%E9%99%90%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA%E5%B9%B6%E8%BF%94%E5%9B%9E%E5%AE%A2%E6%88%B7%E7%AB%AF400%E9%94%99%E8%AF%AF)
  - [当删除一个procedure时,如果待删除的procedure存在,则删除成功](#%E5%BD%93%E5%88%A0%E9%99%A4%E4%B8%80%E4%B8%AAprocedure%E6%97%B6%E5%A6%82%E6%9E%9C%E5%BE%85%E5%88%A0%E9%99%A4%E7%9A%84procedure%E5%AD%98%E5%9C%A8%E5%88%99%E5%88%A0%E9%99%A4%E6%88%90%E5%8A%9F)
  - [创建新的procedure时,如果名称为空字符串,则不允许创建并返回客户端400错误](#%E5%88%9B%E5%BB%BA%E6%96%B0%E7%9A%84procedure%E6%97%B6%E5%A6%82%E6%9E%9C%E5%90%8D%E7%A7%B0%E4%B8%BA%E7%A9%BA%E5%AD%97%E7%AC%A6%E4%B8%B2%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA%E5%B9%B6%E8%BF%94%E5%9B%9E%E5%AE%A2%E6%88%B7%E7%AB%AF400%E9%94%99%E8%AF%AF)
  - [当删除一个procedure时,如果待删除的procedure不存在,则删除成功并返回客户端错误](#%E5%BD%93%E5%88%A0%E9%99%A4%E4%B8%80%E4%B8%AAprocedure%E6%97%B6%E5%A6%82%E6%9E%9C%E5%BE%85%E5%88%A0%E9%99%A4%E7%9A%84procedure%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E5%88%A0%E9%99%A4%E6%88%90%E5%8A%9F%E5%B9%B6%E8%BF%94%E5%9B%9E%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF)
  - [当移动一个procedure时,移动后的排序大于其原先的排序](#%E5%BD%93%E7%A7%BB%E5%8A%A8%E4%B8%80%E4%B8%AAprocedure%E6%97%B6%E7%A7%BB%E5%8A%A8%E5%90%8E%E7%9A%84%E6%8E%92%E5%BA%8F%E5%A4%A7%E4%BA%8E%E5%85%B6%E5%8E%9F%E5%85%88%E7%9A%84%E6%8E%92%E5%BA%8F)
  - [创建新的procedure时,同一看板下已经存在同名,则不允许创建并返回客户端400错误](#%E5%88%9B%E5%BB%BA%E6%96%B0%E7%9A%84procedure%E6%97%B6%E5%90%8C%E4%B8%80%E7%9C%8B%E6%9D%BF%E4%B8%8B%E5%B7%B2%E7%BB%8F%E5%AD%98%E5%9C%A8%E5%90%8C%E5%90%8D%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA%E5%B9%B6%E8%BF%94%E5%9B%9E%E5%AE%A2%E6%88%B7%E7%AB%AF400%E9%94%99%E8%AF%AF)
  - [创建一个新的procedure后,返回自身及links信息](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E6%96%B0%E7%9A%84procedure%E5%90%8E%E8%BF%94%E5%9B%9E%E8%87%AA%E8%BA%AB%E5%8F%8Alinks%E4%BF%A1%E6%81%AF)
- [五、卡片](#%E4%BA%94%E3%80%81%E5%8D%A1%E7%89%87)
  - [当移动一个卡片时,移动后的顺序大于初始顺序](#%E5%BD%93%E7%A7%BB%E5%8A%A8%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E7%A7%BB%E5%8A%A8%E5%90%8E%E7%9A%84%E9%A1%BA%E5%BA%8F%E5%A4%A7%E4%BA%8E%E5%88%9D%E5%A7%8B%E9%A1%BA%E5%BA%8F)
  - [当更新一个卡片时,如果待更新的卡片不存在,则抛出资源不存在的错误](#%E5%BD%93%E6%9B%B4%E6%96%B0%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%BE%85%E6%9B%B4%E6%96%B0%E7%9A%84%E5%8D%A1%E7%89%87%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E6%8A%9B%E5%87%BA%E8%B5%84%E6%BA%90%E4%B8%8D%E5%AD%98%E5%9C%A8%E7%9A%84%E9%94%99%E8%AF%AF)
  - [当创建一个卡片时,如果卡片概述长度超过50,则创建失败](#%E5%BD%93%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8D%A1%E7%89%87%E6%A6%82%E8%BF%B0%E9%95%BF%E5%BA%A6%E8%B6%85%E8%BF%8750%E5%88%99%E5%88%9B%E5%BB%BA%E5%A4%B1%E8%B4%A5)
  - [根据ID查找一个卡片时,如果卡片存在,则返回该卡片](#%E6%A0%B9%E6%8D%AEid%E6%9F%A5%E6%89%BE%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8D%A1%E7%89%87%E5%AD%98%E5%9C%A8%E5%88%99%E8%BF%94%E5%9B%9E%E8%AF%A5%E5%8D%A1%E7%89%87)
  - [当一个卡片从某个procedure移动到另一个procedure时,不仅需要重新排序目标procedure,也要对原始procedure排序](#%E5%BD%93%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E4%BB%8E%E6%9F%90%E4%B8%AAprocedure%E7%A7%BB%E5%8A%A8%E5%88%B0%E5%8F%A6%E4%B8%80%E4%B8%AAprocedure%E6%97%B6%E4%B8%8D%E4%BB%85%E9%9C%80%E8%A6%81%E9%87%8D%E6%96%B0%E6%8E%92%E5%BA%8F%E7%9B%AE%E6%A0%87procedure%E4%B9%9F%E8%A6%81%E5%AF%B9%E5%8E%9F%E5%A7%8Bprocedure%E6%8E%92%E5%BA%8F)
  - [当移动一个卡片时,移动后的顺序小于其前置顺序](#%E5%BD%93%E7%A7%BB%E5%8A%A8%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E7%A7%BB%E5%8A%A8%E5%90%8E%E7%9A%84%E9%A1%BA%E5%BA%8F%E5%B0%8F%E4%BA%8E%E5%85%B6%E5%89%8D%E7%BD%AE%E9%A1%BA%E5%BA%8F)
  - [根据ID查找一个卡片时,如果卡片不存在,则抛出404的错误](#%E6%A0%B9%E6%8D%AEid%E6%9F%A5%E6%89%BE%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8D%A1%E7%89%87%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E6%8A%9B%E5%87%BA404%E7%9A%84%E9%94%99%E8%AF%AF)
  - [更新卡片成功](#%E6%9B%B4%E6%96%B0%E5%8D%A1%E7%89%87%E6%88%90%E5%8A%9F)
  - [当删除一个卡片时,如果待删除的卡片不存在,则抛出404错误](#%E5%BD%93%E5%88%A0%E9%99%A4%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%BE%85%E5%88%A0%E9%99%A4%E7%9A%84%E5%8D%A1%E7%89%87%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E6%8A%9B%E5%87%BA404%E9%94%99%E8%AF%AF)
  - [当根据procedureId查找其下属的卡片时,可以返回其所有卡片](#%E5%BD%93%E6%A0%B9%E6%8D%AEprocedureid%E6%9F%A5%E6%89%BE%E5%85%B6%E4%B8%8B%E5%B1%9E%E7%9A%84%E5%8D%A1%E7%89%87%E6%97%B6%E5%8F%AF%E4%BB%A5%E8%BF%94%E5%9B%9E%E5%85%B6%E6%89%80%E6%9C%89%E5%8D%A1%E7%89%87)
  - [当创建一个卡片时,如果卡片所属的procedure并不存在,则创建失败](#%E5%BD%93%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8D%A1%E7%89%87%E6%89%80%E5%B1%9E%E7%9A%84procedure%E5%B9%B6%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E5%88%9B%E5%BB%BA%E5%A4%B1%E8%B4%A5)
  - [当删除一个卡片时,如果卡片存在,则删除成功](#%E5%BD%93%E5%88%A0%E9%99%A4%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8D%A1%E7%89%87%E5%AD%98%E5%9C%A8%E5%88%99%E5%88%A0%E9%99%A4%E6%88%90%E5%8A%9F)
  - [创建一个新的卡片](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E6%96%B0%E7%9A%84%E5%8D%A1%E7%89%87)
  - [当创建一个卡片时,如果卡片概述为空,则创建失败](#%E5%BD%93%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9C%E5%8D%A1%E7%89%87%E6%A6%82%E8%BF%B0%E4%B8%BA%E7%A9%BA%E5%88%99%E5%88%9B%E5%BB%BA%E5%A4%B1%E8%B4%A5)
  - [当移动一个卡片时,卡片移动后的序号大于其前置序号,但在procedure中它移动后的序号并不是最大的。](#%E5%BD%93%E7%A7%BB%E5%8A%A8%E4%B8%80%E4%B8%AA%E5%8D%A1%E7%89%87%E6%97%B6%E5%8D%A1%E7%89%87%E7%A7%BB%E5%8A%A8%E5%90%8E%E7%9A%84%E5%BA%8F%E5%8F%B7%E5%A4%A7%E4%BA%8E%E5%85%B6%E5%89%8D%E7%BD%AE%E5%BA%8F%E5%8F%B7%E4%BD%86%E5%9C%A8procedure%E4%B8%AD%E5%AE%83%E7%A7%BB%E5%8A%A8%E5%90%8E%E7%9A%84%E5%BA%8F%E5%8F%B7%E5%B9%B6%E4%B8%8D%E6%98%AF%E6%9C%80%E5%A4%A7%E7%9A%84%E3%80%82)
  - [当根据procedureID查找卡片时,如果procedure不存在,则抛出404异常](#%E5%BD%93%E6%A0%B9%E6%8D%AEprocedureid%E6%9F%A5%E6%89%BE%E5%8D%A1%E7%89%87%E6%97%B6%E5%A6%82%E6%9E%9Cprocedure%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E6%8A%9B%E5%87%BA404%E5%BC%82%E5%B8%B8)
- [六、团队](#%E5%85%AD%E3%80%81%E5%9B%A2%E9%98%9F)
  - [创建团队时，如果团队名称为空，则不允许创建](#%E5%88%9B%E5%BB%BA%E5%9B%A2%E9%98%9F%E6%97%B6%EF%BC%8C%E5%A6%82%E6%9E%9C%E5%9B%A2%E9%98%9F%E5%90%8D%E7%A7%B0%E4%B8%BA%E7%A9%BA%EF%BC%8C%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA)
  - [用户根据ID获取team时,如果该team存在,则返回其信息](#%E7%94%A8%E6%88%B7%E6%A0%B9%E6%8D%AEid%E8%8E%B7%E5%8F%96team%E6%97%B6%E5%A6%82%E6%9E%9C%E8%AF%A5team%E5%AD%98%E5%9C%A8%E5%88%99%E8%BF%94%E5%9B%9E%E5%85%B6%E4%BF%A1%E6%81%AF)
  - [创建团队时，如果团队名称超限，则不允许创建](#%E5%88%9B%E5%BB%BA%E5%9B%A2%E9%98%9F%E6%97%B6%EF%BC%8C%E5%A6%82%E6%9E%9C%E5%9B%A2%E9%98%9F%E5%90%8D%E7%A7%B0%E8%B6%85%E9%99%90%EF%BC%8C%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA)
  - [根据用户名获取其所在团队](#%E6%A0%B9%E6%8D%AE%E7%94%A8%E6%88%B7%E5%90%8D%E8%8E%B7%E5%8F%96%E5%85%B6%E6%89%80%E5%9C%A8%E5%9B%A2%E9%98%9F)
  - [创建一个团队](#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E5%9B%A2%E9%98%9F)
  - [创建团队时，如果在本人名下已经存在相同名称的团队，则不允许创建](#%E5%88%9B%E5%BB%BA%E5%9B%A2%E9%98%9F%E6%97%B6%EF%BC%8C%E5%A6%82%E6%9E%9C%E5%9C%A8%E6%9C%AC%E4%BA%BA%E5%90%8D%E4%B8%8B%E5%B7%B2%E7%BB%8F%E5%AD%98%E5%9C%A8%E7%9B%B8%E5%90%8C%E5%90%8D%E7%A7%B0%E7%9A%84%E5%9B%A2%E9%98%9F%EF%BC%8C%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA)
  - [创建团队时，如果未提供团队名称，则不允许创建](#%E5%88%9B%E5%BB%BA%E5%9B%A2%E9%98%9F%E6%97%B6%EF%BC%8C%E5%A6%82%E6%9E%9C%E6%9C%AA%E6%8F%90%E4%BE%9B%E5%9B%A2%E9%98%9F%E5%90%8D%E7%A7%B0%EF%BC%8C%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%88%9B%E5%BB%BA)
- [七、团队邀请](#%E4%B8%83%E3%80%81%E5%9B%A2%E9%98%9F%E9%82%80%E8%AF%B7)
  - [如果邀请人为空,怎不允许发送邀请](#%E5%A6%82%E6%9E%9C%E9%82%80%E8%AF%B7%E4%BA%BA%E4%B8%BA%E7%A9%BA%E6%80%8E%E4%B8%8D%E5%85%81%E8%AE%B8%E5%8F%91%E9%80%81%E9%82%80%E8%AF%B7)
  - [接受邀请>用户接受邀请时,若邀请不存在,则告知用户相关错误](#%E6%8E%A5%E5%8F%97%E9%82%80%E8%AF%B7%E7%94%A8%E6%88%B7%E6%8E%A5%E5%8F%97%E9%82%80%E8%AF%B7%E6%97%B6%E8%8B%A5%E9%82%80%E8%AF%B7%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E5%91%8A%E7%9F%A5%E7%94%A8%E6%88%B7%E7%9B%B8%E5%85%B3%E9%94%99%E8%AF%AF)
  - [用户可以通过用户名邀请其他成员加入到团队中](#%E7%94%A8%E6%88%B7%E5%8F%AF%E4%BB%A5%E9%80%9A%E8%BF%87%E7%94%A8%E6%88%B7%E5%90%8D%E9%82%80%E8%AF%B7%E5%85%B6%E4%BB%96%E6%88%90%E5%91%98%E5%8A%A0%E5%85%A5%E5%88%B0%E5%9B%A2%E9%98%9F%E4%B8%AD)
  - [查看邀请>用户查看邀请时,若邀请不存在,则告知用户相关错误](#%E6%9F%A5%E7%9C%8B%E9%82%80%E8%AF%B7%E7%94%A8%E6%88%B7%E6%9F%A5%E7%9C%8B%E9%82%80%E8%AF%B7%E6%97%B6%E8%8B%A5%E9%82%80%E8%AF%B7%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E5%91%8A%E7%9F%A5%E7%94%A8%E6%88%B7%E7%9B%B8%E5%85%B3%E9%94%99%E8%AF%AF)
  - [如果被邀请人已经是团队的成员,则不允许发送邀请](#%E5%A6%82%E6%9E%9C%E8%A2%AB%E9%82%80%E8%AF%B7%E4%BA%BA%E5%B7%B2%E7%BB%8F%E6%98%AF%E5%9B%A2%E9%98%9F%E7%9A%84%E6%88%90%E5%91%98%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%8F%91%E9%80%81%E9%82%80%E8%AF%B7)
  - [接受邀请>用户接受邀请时,若之前已经接受,则告知用户相关错误](#%E6%8E%A5%E5%8F%97%E9%82%80%E8%AF%B7%E7%94%A8%E6%88%B7%E6%8E%A5%E5%8F%97%E9%82%80%E8%AF%B7%E6%97%B6%E8%8B%A5%E4%B9%8B%E5%89%8D%E5%B7%B2%E7%BB%8F%E6%8E%A5%E5%8F%97%E5%88%99%E5%91%8A%E7%9F%A5%E7%94%A8%E6%88%B7%E7%9B%B8%E5%85%B3%E9%94%99%E8%AF%AF)
  - [如果此前已经存在相同的邀请,则取消之前的邀请](#%E5%A6%82%E6%9E%9C%E6%AD%A4%E5%89%8D%E5%B7%B2%E7%BB%8F%E5%AD%98%E5%9C%A8%E7%9B%B8%E5%90%8C%E7%9A%84%E9%82%80%E8%AF%B7%E5%88%99%E5%8F%96%E6%B6%88%E4%B9%8B%E5%89%8D%E7%9A%84%E9%82%80%E8%AF%B7)
  - [查看邀请>当用户接收到邀请后,可以查看邀请的具体内容](#%E6%9F%A5%E7%9C%8B%E9%82%80%E8%AF%B7%E5%BD%93%E7%94%A8%E6%88%B7%E6%8E%A5%E6%94%B6%E5%88%B0%E9%82%80%E8%AF%B7%E5%90%8E%E5%8F%AF%E4%BB%A5%E6%9F%A5%E7%9C%8B%E9%82%80%E8%AF%B7%E7%9A%84%E5%85%B7%E4%BD%93%E5%86%85%E5%AE%B9)
  - [接受邀请>用户接受邀请,并成功成为团队的一员](#%E6%8E%A5%E5%8F%97%E9%82%80%E8%AF%B7%E7%94%A8%E6%88%B7%E6%8E%A5%E5%8F%97%E9%82%80%E8%AF%B7%E5%B9%B6%E6%88%90%E5%8A%9F%E6%88%90%E4%B8%BA%E5%9B%A2%E9%98%9F%E7%9A%84%E4%B8%80%E5%91%98)
  - [用户可以通过用户名邀请其他成员加入到团队中](#%E7%94%A8%E6%88%B7%E5%8F%AF%E4%BB%A5%E9%80%9A%E8%BF%87%E7%94%A8%E6%88%B7%E5%90%8D%E9%82%80%E8%AF%B7%E5%85%B6%E4%BB%96%E6%88%90%E5%91%98%E5%8A%A0%E5%85%A5%E5%88%B0%E5%9B%A2%E9%98%9F%E4%B8%AD-1)
  - [如果被邀请人不存在,则不允许发送邀请](#%E5%A6%82%E6%9E%9C%E8%A2%AB%E9%82%80%E8%AF%B7%E4%BA%BA%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%8F%91%E9%80%81%E9%82%80%E8%AF%B7)
  - [如果邀请人并非团队的成员则不允许发送邀请](#%E5%A6%82%E6%9E%9C%E9%82%80%E8%AF%B7%E4%BA%BA%E5%B9%B6%E9%9D%9E%E5%9B%A2%E9%98%9F%E7%9A%84%E6%88%90%E5%91%98%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%8F%91%E9%80%81%E9%82%80%E8%AF%B7)
  - [邀请发出后,在消息中心通知用户](#%E9%82%80%E8%AF%B7%E5%8F%91%E5%87%BA%E5%90%8E%E5%9C%A8%E6%B6%88%E6%81%AF%E4%B8%AD%E5%BF%83%E9%80%9A%E7%9F%A5%E7%94%A8%E6%88%B7)
  - [如果邀请加入的团队并不存在,则不允许发送邀请](#%E5%A6%82%E6%9E%9C%E9%82%80%E8%AF%B7%E5%8A%A0%E5%85%A5%E7%9A%84%E5%9B%A2%E9%98%9F%E5%B9%B6%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%8F%91%E9%80%81%E9%82%80%E8%AF%B7)
- [八、团队成员](#%E5%85%AB%E3%80%81%E5%9B%A2%E9%98%9F%E6%88%90%E5%91%98)
  - [加入团队时,如果待加入的成员已经在团队中,则不允许加入](#%E5%8A%A0%E5%85%A5%E5%9B%A2%E9%98%9F%E6%97%B6%E5%A6%82%E6%9E%9C%E5%BE%85%E5%8A%A0%E5%85%A5%E7%9A%84%E6%88%90%E5%91%98%E5%B7%B2%E7%BB%8F%E5%9C%A8%E5%9B%A2%E9%98%9F%E4%B8%AD%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%8A%A0%E5%85%A5)
  - [当用户加入一个团队后，可以获取该团队的所有成员](#%E5%BD%93%E7%94%A8%E6%88%B7%E5%8A%A0%E5%85%A5%E4%B8%80%E4%B8%AA%E5%9B%A2%E9%98%9F%E5%90%8E%EF%BC%8C%E5%8F%AF%E4%BB%A5%E8%8E%B7%E5%8F%96%E8%AF%A5%E5%9B%A2%E9%98%9F%E7%9A%84%E6%89%80%E6%9C%89%E6%88%90%E5%91%98)
  - [加入团队时,如果该团队并不存在,则不允许加入](#%E5%8A%A0%E5%85%A5%E5%9B%A2%E9%98%9F%E6%97%B6%E5%A6%82%E6%9E%9C%E8%AF%A5%E5%9B%A2%E9%98%9F%E5%B9%B6%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E5%8A%A0%E5%85%A5)
  - [若当前用户并非团队成员，则不允许获取](#%E8%8B%A5%E5%BD%93%E5%89%8D%E7%94%A8%E6%88%B7%E5%B9%B6%E9%9D%9E%E5%9B%A2%E9%98%9F%E6%88%90%E5%91%98%EF%BC%8C%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E8%8E%B7%E5%8F%96)
  - [加入一个团队](#%E5%8A%A0%E5%85%A5%E4%B8%80%E4%B8%AA%E5%9B%A2%E9%98%9F)
  - [当用户加入一个团队后，可以获取该团队的所有成员。但是当团队不存在时,则不允许获取。](#%E5%BD%93%E7%94%A8%E6%88%B7%E5%8A%A0%E5%85%A5%E4%B8%80%E4%B8%AA%E5%9B%A2%E9%98%9F%E5%90%8E%EF%BC%8C%E5%8F%AF%E4%BB%A5%E8%8E%B7%E5%8F%96%E8%AF%A5%E5%9B%A2%E9%98%9F%E7%9A%84%E6%89%80%E6%9C%89%E6%88%90%E5%91%98%E3%80%82%E4%BD%86%E6%98%AF%E5%BD%93%E5%9B%A2%E9%98%9F%E4%B8%8D%E5%AD%98%E5%9C%A8%E6%97%B6%E5%88%99%E4%B8%8D%E5%85%81%E8%AE%B8%E8%8E%B7%E5%8F%96%E3%80%82)
- [九、密码](#%E4%B9%9D%E3%80%81%E5%AF%86%E7%A0%81)
  - [用户重置密码后，若再次重置，告知客户端请求无效](#%E7%94%A8%E6%88%B7%E9%87%8D%E7%BD%AE%E5%AF%86%E7%A0%81%E5%90%8E%EF%BC%8C%E8%8B%A5%E5%86%8D%E6%AC%A1%E9%87%8D%E7%BD%AE%EF%BC%8C%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E8%AF%B7%E6%B1%82%E6%97%A0%E6%95%88)
  - [验证码使用后若再次被使用，告示客户端验证码无效](#%E9%AA%8C%E8%AF%81%E7%A0%81%E4%BD%BF%E7%94%A8%E5%90%8E%E8%8B%A5%E5%86%8D%E6%AC%A1%E8%A2%AB%E4%BD%BF%E7%94%A8%EF%BC%8C%E5%91%8A%E7%A4%BA%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%AA%8C%E8%AF%81%E7%A0%81%E6%97%A0%E6%95%88)
  - [用户取得验证码后，和邮箱一起发送到服务端验证，如果验证码正确且未过期，则发送密码重置的链接](#%E7%94%A8%E6%88%B7%E5%8F%96%E5%BE%97%E9%AA%8C%E8%AF%81%E7%A0%81%E5%90%8E%EF%BC%8C%E5%92%8C%E9%82%AE%E7%AE%B1%E4%B8%80%E8%B5%B7%E5%8F%91%E9%80%81%E5%88%B0%E6%9C%8D%E5%8A%A1%E7%AB%AF%E9%AA%8C%E8%AF%81%EF%BC%8C%E5%A6%82%E6%9E%9C%E9%AA%8C%E8%AF%81%E7%A0%81%E6%AD%A3%E7%A1%AE%E4%B8%94%E6%9C%AA%E8%BF%87%E6%9C%9F%EF%BC%8C%E5%88%99%E5%8F%91%E9%80%81%E5%AF%86%E7%A0%81%E9%87%8D%E7%BD%AE%E7%9A%84%E9%93%BE%E6%8E%A5)
  - [当用户请求找回密码时,需要提供邮箱,如果未提供则告知客户端错误](#%E5%BD%93%E7%94%A8%E6%88%B7%E8%AF%B7%E6%B1%82%E6%89%BE%E5%9B%9E%E5%AF%86%E7%A0%81%E6%97%B6%E9%9C%80%E8%A6%81%E6%8F%90%E4%BE%9B%E9%82%AE%E7%AE%B1%E5%A6%82%E6%9E%9C%E6%9C%AA%E6%8F%90%E4%BE%9B%E5%88%99%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF)
  - [用户通过验证码验证,重置密码成功。](#%E7%94%A8%E6%88%B7%E9%80%9A%E8%BF%87%E9%AA%8C%E8%AF%81%E7%A0%81%E9%AA%8C%E8%AF%81%E9%87%8D%E7%BD%AE%E5%AF%86%E7%A0%81%E6%88%90%E5%8A%9F%E3%80%82)
  - [邮箱通过格式校验且存在后，创建密码找回申请记前,如果存在未完成的申请,则将其废弃](#%E9%82%AE%E7%AE%B1%E9%80%9A%E8%BF%87%E6%A0%BC%E5%BC%8F%E6%A0%A1%E9%AA%8C%E4%B8%94%E5%AD%98%E5%9C%A8%E5%90%8E%EF%BC%8C%E5%88%9B%E5%BB%BA%E5%AF%86%E7%A0%81%E6%89%BE%E5%9B%9E%E7%94%B3%E8%AF%B7%E8%AE%B0%E5%89%8D%E5%A6%82%E6%9E%9C%E5%AD%98%E5%9C%A8%E6%9C%AA%E5%AE%8C%E6%88%90%E7%9A%84%E7%94%B3%E8%AF%B7%E5%88%99%E5%B0%86%E5%85%B6%E5%BA%9F%E5%BC%83)
  - [验证码超过五分钟后,验证失败](#%E9%AA%8C%E8%AF%81%E7%A0%81%E8%B6%85%E8%BF%87%E4%BA%94%E5%88%86%E9%92%9F%E5%90%8E%E9%AA%8C%E8%AF%81%E5%A4%B1%E8%B4%A5)
  - [邮箱通过格式校验且存在后，发送找回密码的验证码到邮箱](#%E9%82%AE%E7%AE%B1%E9%80%9A%E8%BF%87%E6%A0%BC%E5%BC%8F%E6%A0%A1%E9%AA%8C%E4%B8%94%E5%AD%98%E5%9C%A8%E5%90%8E%EF%BC%8C%E5%8F%91%E9%80%81%E6%89%BE%E5%9B%9E%E5%AF%86%E7%A0%81%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E5%88%B0%E9%82%AE%E7%AE%B1)
  - [当用户请求找回密码时,需要提供邮箱,如果邮箱不存在则告知客户端错误](#%E5%BD%93%E7%94%A8%E6%88%B7%E8%AF%B7%E6%B1%82%E6%89%BE%E5%9B%9E%E5%AF%86%E7%A0%81%E6%97%B6%E9%9C%80%E8%A6%81%E6%8F%90%E4%BE%9B%E9%82%AE%E7%AE%B1%E5%A6%82%E6%9E%9C%E9%82%AE%E7%AE%B1%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF)
  - [当用户请求找回密码时,需要提供邮箱,如果邮箱格式错误则告知客户端错误](#%E5%BD%93%E7%94%A8%E6%88%B7%E8%AF%B7%E6%B1%82%E6%89%BE%E5%9B%9E%E5%AF%86%E7%A0%81%E6%97%B6%E9%9C%80%E8%A6%81%E6%8F%90%E4%BE%9B%E9%82%AE%E7%AE%B1%E5%A6%82%E6%9E%9C%E9%82%AE%E7%AE%B1%E6%A0%BC%E5%BC%8F%E9%94%99%E8%AF%AF%E5%88%99%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF)
  - [验证码错误,验证失败](#%E9%AA%8C%E8%AF%81%E7%A0%81%E9%94%99%E8%AF%AF%E9%AA%8C%E8%AF%81%E5%A4%B1%E8%B4%A5)
- [十 、消息](#%E5%8D%81-%E3%80%81%E6%B6%88%E6%81%AF)
  - [获取所有消息>用户登录后,可以在消息中心查看所有消息,以便及时处理未读消息或重新查看已读消息](#%E8%8E%B7%E5%8F%96%E6%89%80%E6%9C%89%E6%B6%88%E6%81%AF%E7%94%A8%E6%88%B7%E7%99%BB%E5%BD%95%E5%90%8E%E5%8F%AF%E4%BB%A5%E5%9C%A8%E6%B6%88%E6%81%AF%E4%B8%AD%E5%BF%83%E6%9F%A5%E7%9C%8B%E6%89%80%E6%9C%89%E6%B6%88%E6%81%AF%E4%BB%A5%E4%BE%BF%E5%8F%8A%E6%97%B6%E5%A4%84%E7%90%86%E6%9C%AA%E8%AF%BB%E6%B6%88%E6%81%AF%E6%88%96%E9%87%8D%E6%96%B0%E6%9F%A5%E7%9C%8B%E5%B7%B2%E8%AF%BB%E6%B6%88%E6%81%AF)
  - [获取指定消息>用户可以在消息中心查看某条具体的消息,查看完毕后将该条消息设置为已读](#%E8%8E%B7%E5%8F%96%E6%8C%87%E5%AE%9A%E6%B6%88%E6%81%AF%E7%94%A8%E6%88%B7%E5%8F%AF%E4%BB%A5%E5%9C%A8%E6%B6%88%E6%81%AF%E4%B8%AD%E5%BF%83%E6%9F%A5%E7%9C%8B%E6%9F%90%E6%9D%A1%E5%85%B7%E4%BD%93%E7%9A%84%E6%B6%88%E6%81%AF%E6%9F%A5%E7%9C%8B%E5%AE%8C%E6%AF%95%E5%90%8E%E5%B0%86%E8%AF%A5%E6%9D%A1%E6%B6%88%E6%81%AF%E8%AE%BE%E7%BD%AE%E4%B8%BA%E5%B7%B2%E8%AF%BB)
  - [获取未读消息数>用户登录后,可以获取未读消息数量,以便在醒目位置显示提醒用户及时处理](#%E8%8E%B7%E5%8F%96%E6%9C%AA%E8%AF%BB%E6%B6%88%E6%81%AF%E6%95%B0%E7%94%A8%E6%88%B7%E7%99%BB%E5%BD%95%E5%90%8E%E5%8F%AF%E4%BB%A5%E8%8E%B7%E5%8F%96%E6%9C%AA%E8%AF%BB%E6%B6%88%E6%81%AF%E6%95%B0%E9%87%8F%E4%BB%A5%E4%BE%BF%E5%9C%A8%E9%86%92%E7%9B%AE%E4%BD%8D%E7%BD%AE%E6%98%BE%E7%A4%BA%E6%8F%90%E9%86%92%E7%94%A8%E6%88%B7%E5%8F%8A%E6%97%B6%E5%A4%84%E7%90%86)
  - [获取指定消息>查看某条具体的消息时，如果该消息已读，则加载后不必再设置为已读](#%E8%8E%B7%E5%8F%96%E6%8C%87%E5%AE%9A%E6%B6%88%E6%81%AF%E6%9F%A5%E7%9C%8B%E6%9F%90%E6%9D%A1%E5%85%B7%E4%BD%93%E7%9A%84%E6%B6%88%E6%81%AF%E6%97%B6%EF%BC%8C%E5%A6%82%E6%9E%9C%E8%AF%A5%E6%B6%88%E6%81%AF%E5%B7%B2%E8%AF%BB%EF%BC%8C%E5%88%99%E5%8A%A0%E8%BD%BD%E5%90%8E%E4%B8%8D%E5%BF%85%E5%86%8D%E8%AE%BE%E7%BD%AE%E4%B8%BA%E5%B7%B2%E8%AF%BB)
- [十一 、任务认领](#%E5%8D%81%E4%B8%80-%E3%80%81%E4%BB%BB%E5%8A%A1%E8%AE%A4%E9%A2%86)
  - [当用户根据cardID获取分配记录时,如果指定的卡片并不存在,则返回404客户端错误](#%E5%BD%93%E7%94%A8%E6%88%B7%E6%A0%B9%E6%8D%AEcardid%E8%8E%B7%E5%8F%96%E5%88%86%E9%85%8D%E8%AE%B0%E5%BD%95%E6%97%B6%E5%A6%82%E6%9E%9C%E6%8C%87%E5%AE%9A%E7%9A%84%E5%8D%A1%E7%89%87%E5%B9%B6%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E8%BF%94%E5%9B%9E404%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF)
  - [当用户根据cardID获取分配记录时,如果指定的卡片存在,则返回分配记录集合](#%E5%BD%93%E7%94%A8%E6%88%B7%E6%A0%B9%E6%8D%AEcardid%E8%8E%B7%E5%8F%96%E5%88%86%E9%85%8D%E8%AE%B0%E5%BD%95%E6%97%B6%E5%A6%82%E6%9E%9C%E6%8C%87%E5%AE%9A%E7%9A%84%E5%8D%A1%E7%89%87%E5%AD%98%E5%9C%A8%E5%88%99%E8%BF%94%E5%9B%9E%E5%88%86%E9%85%8D%E8%AE%B0%E5%BD%95%E9%9B%86%E5%90%88)
  - [当用户想取消某个分配时,如果指定的分配记录并不存在,则返回404客户端错误](#%E5%BD%93%E7%94%A8%E6%88%B7%E6%83%B3%E5%8F%96%E6%B6%88%E6%9F%90%E4%B8%AA%E5%88%86%E9%85%8D%E6%97%B6%E5%A6%82%E6%9E%9C%E6%8C%87%E5%AE%9A%E7%9A%84%E5%88%86%E9%85%8D%E8%AE%B0%E5%BD%95%E5%B9%B6%E4%B8%8D%E5%AD%98%E5%9C%A8%E5%88%99%E8%BF%94%E5%9B%9E404%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF)
  - [当用户根据ID查找分配记录时,如果该记录存在则将其返回](#%E5%BD%93%E7%94%A8%E6%88%B7%E6%A0%B9%E6%8D%AEid%E6%9F%A5%E6%89%BE%E5%88%86%E9%85%8D%E8%AE%B0%E5%BD%95%E6%97%B6%E5%A6%82%E6%9E%9C%E8%AF%A5%E8%AE%B0%E5%BD%95%E5%AD%98%E5%9C%A8%E5%88%99%E5%B0%86%E5%85%B6%E8%BF%94%E5%9B%9E)
  - [当用户想取消某个分配时,如果指定的分配记录存在,则成功将其取消](#%E5%BD%93%E7%94%A8%E6%88%B7%E6%83%B3%E5%8F%96%E6%B6%88%E6%9F%90%E4%B8%AA%E5%88%86%E9%85%8D%E6%97%B6%E5%A6%82%E6%9E%9C%E6%8C%87%E5%AE%9A%E7%9A%84%E5%88%86%E9%85%8D%E8%AE%B0%E5%BD%95%E5%AD%98%E5%9C%A8%E5%88%99%E6%88%90%E5%8A%9F%E5%B0%86%E5%85%B6%E5%8F%96%E6%B6%88)
  - [成功创建一条分配记录](#%E6%88%90%E5%8A%9F%E5%88%9B%E5%BB%BA%E4%B8%80%E6%9D%A1%E5%88%86%E9%85%8D%E8%AE%B0%E5%BD%95)
- [领域未定义](#%E9%A2%86%E5%9F%9F%E6%9C%AA%E5%AE%9A%E4%B9%89)
  - [当头部信息的userName和路径中的不一致时,告知客户端错误](#%E5%BD%93%E5%A4%B4%E9%83%A8%E4%BF%A1%E6%81%AF%E7%9A%84username%E5%92%8C%E8%B7%AF%E5%BE%84%E4%B8%AD%E7%9A%84%E4%B8%8D%E4%B8%80%E8%87%B4%E6%97%B6%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%94%99%E8%AF%AF)
  - [当token不为空且未失效时,请求到达后更新token的有效期](#%E5%BD%93token%E4%B8%8D%E4%B8%BA%E7%A9%BA%E4%B8%94%E6%9C%AA%E5%A4%B1%E6%95%88%E6%97%B6%E8%AF%B7%E6%B1%82%E5%88%B0%E8%BE%BE%E5%90%8E%E6%9B%B4%E6%96%B0token%E7%9A%84%E6%9C%89%E6%95%88%E6%9C%9F)
  - [如果用户在5分钟内未发送请求,token将会失效,告知客户端需要重新授权](#%E5%A6%82%E6%9E%9C%E7%94%A8%E6%88%B7%E5%9C%A85%E5%88%86%E9%92%9F%E5%86%85%E6%9C%AA%E5%8F%91%E9%80%81%E8%AF%B7%E6%B1%82token%E5%B0%86%E4%BC%9A%E5%A4%B1%E6%95%88%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%9C%80%E8%A6%81%E9%87%8D%E6%96%B0%E6%8E%88%E6%9D%83)
  - [当请求需要认证时,如果没有携带token,则告知客户端需要授权](#%E5%BD%93%E8%AF%B7%E6%B1%82%E9%9C%80%E8%A6%81%E8%AE%A4%E8%AF%81%E6%97%B6%E5%A6%82%E6%9E%9C%E6%B2%A1%E6%9C%89%E6%90%BA%E5%B8%A6token%E5%88%99%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%9C%80%E8%A6%81%E6%8E%88%E6%9D%83)
  - [当token中的用户名与header中携带的用户名不一致时,告知客户端认证未通过](#%E5%BD%93token%E4%B8%AD%E7%9A%84%E7%94%A8%E6%88%B7%E5%90%8D%E4%B8%8Eheader%E4%B8%AD%E6%90%BA%E5%B8%A6%E7%9A%84%E7%94%A8%E6%88%B7%E5%90%8D%E4%B8%8D%E4%B8%80%E8%87%B4%E6%97%B6%E5%91%8A%E7%9F%A5%E5%AE%A2%E6%88%B7%E7%AB%AF%E8%AE%A4%E8%AF%81%E6%9C%AA%E9%80%9A%E8%BF%87)
  - [当用户请求登录或注册时,首先需要向系统发送一次认证请求,将公钥发送至客户端](#%E5%BD%93%E7%94%A8%E6%88%B7%E8%AF%B7%E6%B1%82%E7%99%BB%E5%BD%95%E6%88%96%E6%B3%A8%E5%86%8C%E6%97%B6%E9%A6%96%E5%85%88%E9%9C%80%E8%A6%81%E5%90%91%E7%B3%BB%E7%BB%9F%E5%8F%91%E9%80%81%E4%B8%80%E6%AC%A1%E8%AE%A4%E8%AF%81%E8%AF%B7%E6%B1%82%E5%B0%86%E5%85%AC%E9%92%A5%E5%8F%91%E9%80%81%E8%87%B3%E5%AE%A2%E6%88%B7%E7%AB%AF)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->


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
	"timestamp":1474676954581,
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
	"timestamp":1474676954696,
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
	"timestamp":1474676954792,
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
	"password":"HutU9BNGSrnCyfByHhip0xDesbAqkJBJD38X7QuGDk2pW0Wls1Waj+sDUj+C5flyjpRcBb2KWl6skfA31n4BT8OQTJEg+8X6dcOyIyutILNEXTPfClOxc+sjeOaR4dXyTIIZMRz9tOh2+SJH5Xbwg42himoX7jnsax/R8xrVCak=",
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
http://localhost:8007/login?identity=someone&password=BjQ2JIbU%2BxyiOqel2UWuDAmgmdNrUyiLhmsnTgG3khSmNmvwNv4BShXAzWZ%2FRmUD9cp6fyoixO%2BItvKI1JNJj8oHKx%2FgsgiThWc%2BvjhOVa8YPk7B8%2FrP4JM0TTbmDxfL0%2Fm89zZRbaJngeLTNIZhjE7o3v1fKhpF3alryHlTYFw%3D

**响应体**
```
{
	"timestamp":1474676949418,
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
http://localhost:8007/login?identity=someone&password=Oawm02FXOEvOoyJX66v6T4vT2KF8IiPQ2xbKEdyFn%2BKbMauy%2FW9H5eBQywYi1EViNPCXFzWQgzPb49rmwtSqt%2F42QAldr1fbQ%2FYMbaFBnoGl6dEXKhHMPdIoGcpeePRW8v1l%2F%2FH2lO1y%2Bwz8W7GaiBB4HTNj3jNQfQAovd1dp7I%3D

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
	"timestamp":1474676949698,
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
	"timestamp":1474676949790,
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
	"creationTime":"2016-09-24 08:29:00.252000",
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
	"modificationTime":"2016-09-24 08:29:00.252000",
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
	"timestamp":1474676940382,
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
	"creationTime":"2016-09-24 08:29:00.403000",
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
	"modificationTime":"2016-09-24 08:29:00.403000",
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
	"timestamp":1474676940492,
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
		"creationTime":"2016-09-24 08:29:00.510000",
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
		"modificationTime":"2016-09-24 08:29:00.510000",
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
	"creationTime":"2016-09-24 08:29:00.581000",
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
	"modificationTime":"2016-09-24 08:29:00.581000",
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
	"timestamp":1474676940668,
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
	"timestamp":1474676940694,
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
	"creationTime":"2016-09-24 08:29:13.689000",
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
	"modificationTime":"2016-09-24 08:29:13.689000",
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
	"creationTime":"2016-09-24 08:29:13.779000",
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
	"modificationTime":"2016-09-24 08:29:13.779000",
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
	"creationTime":"2016-09-24 08:29:13.834000",
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
	"modificationTime":"2016-09-24 08:29:13.834000",
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
	"creationTime":"2016-09-24 08:29:13.901000",
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
	"modificationTime":"2016-09-24 08:29:13.901000",
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
	"timestamp":1474676953981,
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
	"timestamp":1474676954022,
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
		"creationTime":"2016-09-24 08:29:14.044000",
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
		"modificationTime":"2016-09-24 08:29:14.044000",
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
	"timestamp":1474676954143,
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
	"timestamp":1474676954224,
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
	"timestamp":1474676954251,
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
	"creationTime":"2016-09-24 08:29:14.276000",
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
	"modificationTime":"2016-09-24 08:29:14.276000",
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
	"timestamp":1474676954370,
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
	"creationTime":"2016-09-24 08:29:14.411000",
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
	"modificationTime":"2016-09-24 08:29:14.411000",
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
	"creationTime":"2016-09-24 08:29:00.717000",
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
	"modificationTime":"2016-09-24 08:29:00.717000",
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
	"timestamp":1474676941178,
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
	"timestamp":1474676941243,
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
	"creationTime":"2016-09-24 08:29:01.258000",
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
	"modificationTime":"2016-09-24 08:29:01.258000",
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
	"creationTime":"2016-09-24 08:29:01.334000",
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
	"modificationTime":"2016-09-24 08:29:01.334000",
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
	"creationTime":"2016-09-24 08:29:01.440000",
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
	"modificationTime":"2016-09-24 08:29:01.440000",
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
	"timestamp":1474676941556,
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
	"creationTime":"2016-09-24 08:29:01.591000",
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
	"modificationTime":"2016-09-24 08:29:01.591000",
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
	"timestamp":1474676941723,
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
		"creationTime":"2016-09-24 08:29:01.747000",
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
		"modificationTime":"2016-09-24 08:29:01.747000",
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
	"timestamp":1474676941940,
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
	"creationTime":"2016-09-24 08:29:02.110000",
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
	"modificationTime":"2016-09-24 08:29:02.110000",
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
	"timestamp":1474676942261,
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
	"creationTime":"2016-09-24 08:29:02.299000",
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
	"modificationTime":"2016-09-24 08:29:02.299000",
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
	"timestamp":1474676942500,
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
	"timestamp":1474676962470,
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
	"creationTime":"2016-09-24 08:29:22.523000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 08:29:22.523000",
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
	"timestamp":1474676962678,
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
		"creationTime":"2016-09-24 08:29:22.716000",
		"_links":{
			"members":{
				"href":"http://localhost:8007/teams/fooId/members"
			},
			"self":{
				"href":"http://localhost:8007/teams/fooId"
			}
		},
		"author":"someone",
		"modificationTime":"2016-09-24 08:29:22.716000",
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
	"creationTime":"2016-09-24 08:29:22.785000",
	"_links":{
		"members":{
			"href":"http://localhost:8007/teams/fooId/members"
		},
		"self":{
			"href":"http://localhost:8007/teams/fooId"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 08:29:22.785000",
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
	"timestamp":1474676962840,
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
	"timestamp":1474676962904,
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
	"timestamp":1474676955717,
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
	"timestamp":1474676955758,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请函不存在。",
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
	"creationTime":"2016-09-24 08:29:15.859000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 08:29:15.859000",
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
	"timestamp":1474676957404,
	"status":400,
	"error":"Bad Request",
	"exception":"org.thiki.kanban.foundation.exception.BusinessException",
	"message":"邀请函不存在。",
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
	"timestamp":1474676957473,
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
	"timestamp":1474676957508,
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
	"creationTime":"2016-09-24 08:29:17.645000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 08:29:17.645000",
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
	"creationTime":"2016-09-24 08:29:18.952000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 08:29:18.952000",
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
	"creationTime":"2016-09-24 08:29:19.072000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/invitation-id"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 08:29:19.072000",
	"isAccepted":true,
	"teamId":"foo-team-id",
	"inviter":"someone",
	"id":"invitation-id",
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
	"isAccepted":false,
	"invitee":"invitee-user"
}
```


**响应体**
```
{
	"creationTime":"2016-09-24 08:29:19.220000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 08:29:19.220000",
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
	"timestamp":1474676960782,
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
	"timestamp":1474676960845,
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
	"creationTime":"2016-09-24 08:29:20.902000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-team-Id/members/invitation/fooId"
		},
		"team":{
			"href":"http://localhost:8007/teams/foo-team-Id"
		}
	},
	"modificationTime":"2016-09-24 08:29:20.902000",
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
	"timestamp":1474676962354,
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
	"timestamp":1474676955059,
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
	"timestamp":1474676955297,
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
	"timestamp":1474676955462,
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
	"creationTime":"2016-09-24 08:29:15.519000",
	"_links":{
		"self":{
			"href":"http://localhost:8007/teams/foo-teamId/teamMembers"
		}
	},
	"author":"someone",
	"modificationTime":"2016-09-24 08:29:15.519000",
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
	"timestamp":1474676955624,
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
	"timestamp":1474676950253,
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
	"timestamp":1474676950298,
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
	"timestamp":1474676950444,
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
	"password":"dI7sTqLxdKQCf0W8TvNhYxrhM7OI3qC+Q+q8Lx24twQftL2kDnJ9ZlaVl9kGB8S5N/wADKkcyB+uHnIX1f6tK9JVc0yNSWWSKojYa448LPOFHPHlKkv2al6d75wl18CzNkM4DxeH+l6Lc0QhHokQu0qHMivQ7gpQwU7vvZEBaCg="
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
	"timestamp":1474676952022,
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
	"timestamp":1474676953553,
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
	"timestamp":1474676953597,
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
	"timestamp":1474676953637,
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
			"creationTime":"2016-09-24 08:29:09.828000",
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
	"creationTime":"2016-09-24 08:29:09.963000",
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
	"creationTime":"2016-09-24 08:29:10.099000",
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
	"timestamp":1474676939122,
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
	"timestamp":1474676939871,
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
	"creationTime":"2016-09-24 08:28:59.900000",
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
	"modificationTime":"2016-09-24 08:28:59.900000",
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
	"creationTime":"2016-09-24 08:29:00.153000",
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
	"modificationTime":"2016-09-24 08:29:00.153000",
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
	"timestamp":1474676942690,
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
	"timestamp":1474676949010,
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
	"timestamp":1474676949079,
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
	"timestamp":1474676949140,
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
	"timestamp":1474676949205,
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
