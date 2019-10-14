1.在docker服务器上将公共的组件库：common-softs拖进去，里面集成了常用工具

cd 到相应的组件文件夹中，启用服务

例如：需要使用mysql，则cd到common-softs\mysql
  安装服务：docker-compose up -d
  卸载服务：docker-compose down
  
  停止服务：docker-compose stop
  启动服务：docker-compose start
  