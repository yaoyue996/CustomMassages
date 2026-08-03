# CustomMessages for Paper 1.21.4

自定义全服消息插件——进服、退服、死亡、成就广播消息。

## 使用

1. 将 `CustomMessages-1.21.4.jar` 放入 `plugins/` 目录
2. 重启服务器或执行 `/reload`
3. 编辑 `plugins/CustomMessages/config.yml` 自定义消息格式
4. 再次 `/reload` 使配置生效

## 配置

```yaml
# 进服消息 - 占位符 {player}
join-message: "&a[+] &f{player}"

# 退服消息 - 占位符 {player}
quit-message: "&c[-] &f{player}"

# 死亡消息 - 占位符 {player} {message}
death-message: "&e💀 &f{player} &7{message}"

# 成就消息 - 占位符 {player} {advancement}
advancement-message: "&d🏆 &f{player} &ecompleted &6{advancement}"
```

### 颜色代码

| 代码 | 颜色 | 代码 | 样式 |
|------|------|------|------|
| `&0` | 黑色 | `&l` | **粗体** |
| `&1` | 深蓝 | `&m` | ~~删除线~~ |
| `&2` | 深绿 | `&n` | 下划线 |
| `&3` | 深青 | `&o` | *斜体* |
| `&4` | 深红 | `&r` | 重置 |
| `&5` | 紫色 | | |
| `&6` | 金色 | | |
| `&7` | 灰色 | | |
| `&8` | 深灰 | | |
| `&9` | 蓝色 | | |
| `&a` | 绿色 | | |
| `&b` | 青色 | | |
| `&c` | 红色 | | |
| `&d` | 粉色 | | |
| `&e` | 黄色 | | |
| `&f` | 白色 | | |

### 占位符

| 占位符 | 说明 |
|--------|------|
| `{player}` | 玩家名称 |
| `{message}` | 死因原文 |
| `{advancement}` | 成就/进度名称 |

## 功能说明

- **进服/退服**：修改 `PlayerJoinEvent` / `PlayerQuitEvent` 的广播消息
- **死亡**：从原版死亡消息中提取死因，配合自定义格式广播
- **成就**：支持进度系统，使用 Adventure Component API 和 `AdvancementDisplay` 过滤。

## 需求

| 项目 | 要求 |
|------|------|
| 服务端 | Paper 1.21.4 |
| 依赖 | 无 |
