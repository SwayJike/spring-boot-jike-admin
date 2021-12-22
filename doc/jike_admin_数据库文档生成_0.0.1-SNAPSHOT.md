# 数据库文档

**数据库名：** jike_admin

**文档版本：** 0.0.1-SNAPSHOT

**文档描述：** 数据库文档生成

| 表名                  | 说明       |
| :---: | :---: |
| [sys_dept](#sys_dept) | 部门 |
| [sys_job](#sys_job) | 岗位 |
| [sys_menu](#sys_menu) | 系统菜单 |
| [sys_role](#sys_role) | 角色表 |
| [sys_roles_depts](#sys_roles_depts) | 角色部门关联 |
| [sys_roles_menus](#sys_roles_menus) | 角色菜单关联 |
| [sys_user](#sys_user) | 系统用户 |
| [sys_users_jobs](#sys_users_jobs) |  |
| [sys_users_roles](#sys_users_roles) | 用户角色关联 |

**表名：** <a id="sys_dept">sys_dept</a>

**说明：** 部门

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | dept_id |   bigint   | 20 |   0    |    N     |  Y   |       | ID  |
|  2   | pid |   bigint   | 20 |   0    |    Y     |  N   |       | 上级部门  |
|  3   | sub_count |   int   | 10 |   0    |    Y     |  N   |   0    | 子部门数目  |
|  4   | name |   varchar   | 255 |   0    |    N     |  N   |       | 名称  |
|  5   | dept_sort |   int   | 10 |   0    |    Y     |  N   |   999    | 排序  |
|  6   | enabled |   bit   | 1 |   0    |    N     |  N   |       | 状态  |
|  7   | create_by |   varchar   | 255 |   0    |    Y     |  N   |       | 创建者  |
|  8   | update_by |   varchar   | 255 |   0    |    Y     |  N   |       | 更新者  |
|  9   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建日期  |
|  10   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="sys_job">sys_job</a>

**说明：** 岗位

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | job_id |   bigint   | 20 |   0    |    N     |  Y   |       | ID  |
|  2   | name |   varchar   | 255 |   0    |    N     |  N   |       | 岗位名称  |
|  3   | enabled |   bit   | 1 |   0    |    N     |  N   |       | 岗位状态  |
|  4   | job_sort |   int   | 10 |   0    |    Y     |  N   |       | 排序  |
|  5   | create_by |   varchar   | 255 |   0    |    Y     |  N   |       | 创建者  |
|  6   | update_by |   varchar   | 255 |   0    |    Y     |  N   |       | 更新者  |
|  7   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建日期  |
|  8   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="sys_menu">sys_menu</a>

**说明：** 系统菜单

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | menu_id |   bigint   | 20 |   0    |    N     |  Y   |       | ID  |
|  2   | pid |   bigint   | 20 |   0    |    Y     |  N   |       | 上级菜单ID  |
|  3   | sub_count |   int   | 10 |   0    |    Y     |  N   |   0    | 子菜单数目  |
|  4   | type |   int   | 10 |   0    |    Y     |  N   |       | 菜单类型  |
|  5   | title |   varchar   | 255 |   0    |    Y     |  N   |       | 菜单标题  |
|  6   | name |   varchar   | 255 |   0    |    Y     |  N   |       | 组件名称  |
|  7   | component |   varchar   | 255 |   0    |    Y     |  N   |       | 组件  |
|  8   | menu_sort |   int   | 10 |   0    |    Y     |  N   |       | 排序  |
|  9   | icon |   varchar   | 255 |   0    |    Y     |  N   |       | 图标  |
|  10   | path |   varchar   | 255 |   0    |    Y     |  N   |       | 链接地址  |
|  11   | i_frame |   bit   | 1 |   0    |    Y     |  N   |       | 是否外链  |
|  12   | cache |   bit   | 1 |   0    |    Y     |  N   |   b'0'    | 缓存  |
|  13   | hidden |   bit   | 1 |   0    |    Y     |  N   |   b'0'    | 隐藏  |
|  14   | permission |   varchar   | 255 |   0    |    Y     |  N   |       | 权限  |
|  15   | create_by |   varchar   | 255 |   0    |    Y     |  N   |       | 创建者  |
|  16   | update_by |   varchar   | 255 |   0    |    Y     |  N   |       | 更新者  |
|  17   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建日期  |
|  18   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="sys_role">sys_role</a>

**说明：** 角色表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | role_id |   bigint   | 20 |   0    |    N     |  Y   |       | ID  |
|  2   | name |   varchar   | 255 |   0    |    N     |  N   |       | 名称  |
|  3   | level |   int   | 10 |   0    |    Y     |  N   |       | 角色级别  |
|  4   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 描述  |
|  5   | data_scope |   varchar   | 255 |   0    |    Y     |  N   |       | 数据权限  |
|  6   | create_by |   varchar   | 255 |   0    |    Y     |  N   |       | 创建者  |
|  7   | update_by |   varchar   | 255 |   0    |    Y     |  N   |       | 更新者  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建日期  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="sys_roles_depts">sys_roles_depts</a>

**说明：** 角色部门关联

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | role_id |   bigint   | 20 |   0    |    N     |  Y   |       |   |
|  2   | dept_id |   bigint   | 20 |   0    |    N     |  Y   |       |   |

**表名：** <a id="sys_roles_menus">sys_roles_menus</a>

**说明：** 角色菜单关联

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | menu_id |   bigint   | 20 |   0    |    N     |  Y   |       | 菜单ID  |
|  2   | role_id |   bigint   | 20 |   0    |    N     |  Y   |       | 角色ID  |

**表名：** <a id="sys_user">sys_user</a>

**说明：** 系统用户

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   bigint   | 20 |   0    |    N     |  Y   |       | ID  |
|  2   | dept_id |   bigint   | 20 |   0    |    Y     |  N   |       | 部门名称  |
|  3   | username |   varchar   | 255 |   0    |    Y     |  N   |       | 用户名  |
|  4   | nick_name |   varchar   | 255 |   0    |    Y     |  N   |       | 昵称  |
|  5   | gender |   varchar   | 2 |   0    |    Y     |  N   |       | 性别  |
|  6   | phone |   varchar   | 255 |   0    |    Y     |  N   |       | 手机号码  |
|  7   | email |   varchar   | 255 |   0    |    Y     |  N   |       | 邮箱  |
|  8   | avatar_name |   varchar   | 255 |   0    |    Y     |  N   |       | 头像地址  |
|  9   | avatar_path |   varchar   | 255 |   0    |    Y     |  N   |       | 头像真实路径  |
|  10   | password |   varchar   | 255 |   0    |    Y     |  N   |       | 密码  |
|  11   | is_admin |   bit   | 1 |   0    |    Y     |  N   |   b'0'    | 是否为admin账号  |
|  12   | enabled |   bigint   | 20 |   0    |    Y     |  N   |       | 状态：1启用、0禁用  |
|  13   | create_by |   varchar   | 255 |   0    |    Y     |  N   |       | 创建者  |
|  14   | update_by |   varchar   | 255 |   0    |    Y     |  N   |       | 更新者  |
|  15   | pwd_reset_time |   datetime   | 19 |   0    |    Y     |  N   |       | 修改密码的时间  |
|  16   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建日期  |
|  17   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="sys_users_jobs">sys_users_jobs</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   bigint   | 20 |   0    |    N     |  Y   |       | 用户ID  |
|  2   | job_id |   bigint   | 20 |   0    |    N     |  Y   |       | 岗位ID  |

**表名：** <a id="sys_users_roles">sys_users_roles</a>

**说明：** 用户角色关联

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   bigint   | 20 |   0    |    N     |  Y   |       | 用户ID  |
|  2   | role_id |   bigint   | 20 |   0    |    N     |  Y   |       | 角色ID  |
