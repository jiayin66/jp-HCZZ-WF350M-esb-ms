给杨佳维的一些话：

欢迎你开发此微服务，在开发之前，需要注意一些事项：

1，该微服务由jp-boot-business-archetype骨架生成，默认导入以下依赖：
&nbsp;&nbsp;1，spring boot data jpa
&nbsp;&nbsp;2，ojdbc-6

2，一般微服务要求以jp-boot-starter-parent来作为父项目。这里开发人员最好最好不要改

3，微服务的yml配置文件默认会放在src/test/resources目录下，对应四个profile：local（本地运行使用），dev（开发环境上运行使用），test（小集成环境上运行使用），pro（生产环境上运行使用）。你们可以检查一下配置是否正确，或调整配置，确认无误后交给志龙放在配置中心，省去自己编写yml配置文件的烦恼。