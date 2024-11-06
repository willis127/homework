# 使用官方 Redis 镜像作为基础镜像
FROM redis:6.2-alpine

# 将 Redis 配置文件复制到容器中（可选）
# 如果有自定义的 redis.conf 文件，取消以下两行的注释
# COPY redis.conf /usr/local/etc/redis/redis.conf

# 暴露 Redis 默认端口 6379
EXPOSE 6379

# 使用默认的 Redis 配置文件启动 Redis 服务
# 如果有自定义的 redis.conf，使用以下命令行：
# CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]
CMD ["redis-server"]