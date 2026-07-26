# CustomMessages

Paper 1.12.2 自定义全服消息插件——进服、退服、死亡、成就广播消息，全部可配置。

## 功能

- **进服消息** — 玩家加入服务器时的欢迎消息
- **退服消息** — 玩家离开服务器时的告别消息
- **死亡消息** — 玩家死亡时广播死因（保留原版死因文本）
- **成就消息** — 获得成就时全服广播（同时支持新进度系统和旧成就系统）

## 需求

| 项目 | 要求 |
|------|------|
| 服务端 | Paper 1.12.2 |
| Java | 8+ |
| 依赖 | 无（不需要 PlaceholderAPI、ProtocolLib） |

## 安装

1. 下载 `CustomMessages-{version}.jar`
2. 放入 `plugins/` 目录
3. 重启服务器（或执行 `/reload`）
4. 首次启动后自动生成 `plugins/CustomMessages/config.yml`

## 配置

编辑 `plugins/CustomMessages/config.yml`，支持 `&` 颜色代码。

```yaml
# 进服消息
join-message: "&a[+] &f{player}"

# 退服消息
quit-message: "&c[-] &f{player}"

# 死亡消息
death-message: "&e💀 &f{player} &7{message}"

# 成就消息（同时作用于进度和旧成就）
advancement-message: "&d🏆 &f{player} &ecompleted &6{advancement}"
```

### 颜色代码

| 代码 | 颜色 | 代码 | 颜色 | 代码 | 样式 |
|------|------|------|------|------|------|
| `&0` | 黑色 | `&6` | 金色 | `&k` | 乱码 |
| `&1` | 深蓝 | `&7` | 灰色 | `&l` | **粗体** |
| `&2` | 深绿 | `&8` | 深灰 | `&m` | ~~删除线~~ |
| `&3` | 深青 | `&9` | 蓝色 | `&n` | <u>下划线</u> |
| `&4` | 深红 | `&a` | 绿色 | `&o` | *斜体* |
| `&5` | 紫色 | `&b` | 青色 | `&r` | 重置 |
| `&e` | 黄色 | `&c` | 红色 | | |
| | | `&d` | 粉色 | | |
| | | `&f` | 白色 | | |

### 占位符

| 占位符 | 适用消息 | 说明 |
|--------|----------|------|
| `{player}` | 全部 | 玩家名称 |
| `{message}` | 死亡消息 | 死因原文（如 `was slain by Zombie`） |
| `{advancement}` | 成就消息 | 成就/进度名称（自动格式化） |

## 命令与权限

本插件无命令，无权限节点。安装即用，修改 `config.yml` 后 `/reload` 即可生效。

## 注意事项

- **死亡消息**：`{message}` 占位符会从原版死亡消息中自动剥离玩家名，仅保留死因文本。
- **成就消息**：插件同时监听 `PlayerAdvancementDoneEvent`（1.12 新进度系统）和 `PlayerAchievementAwardedEvent`（旧成就系统）。配方解锁（`recipes/`）和根进度（`/root`）不会触发广播。
- **原版成就公告**：Paper 1.12.2 的 `PlayerAdvancementDoneEvent` 不支持关闭原版公告。如需仅显示自定义消息，请在游戏内执行 `/gamerule announceAdvancements false`。

## 构建

```bash
git clone <repo-url>
cd CustomMessages
mvn clean package
```

生成的 `jar` 位于 `target/CustomMessages-{version}.jar`。
