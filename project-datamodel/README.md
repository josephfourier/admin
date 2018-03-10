# project-datamodel

本目录包含数据模型、数据库脚本等

## 文件说明

- zjy.apm： 上次修改数据模型快照（file-->save as保存格式为Archived PDM(XML)得到）

- zjy.pdm： 数据模型

- zjy.png： 模型图

- zjy.sql： 最新脚本

## 操作说明

- PowerDesigner打开`zjy.pdm`修改

- 选择database-->apply model changes to database选择sql保存路径，在get database schema一栏中选择using an archive model选择`zjy.apm`，得到只有更新升级的脚本

- 运行更新升级脚本，得到最新数据结构（保留旧数据），导出得到最新`zjy.sql`

- PowerDesigner全选，Edit-->Export Image...导出`zjy.png`

- PowerDesigner导出`zjy.apm`

- PowerDesigner保存得到`zjy.pdm`