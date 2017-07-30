http://mp.weixin.qq.com/s?__biz=MjM5NzMyMjAwMA==&mid=2651478829&idx=2&sn=f4e36a720e753b0b63c70aa036e41c73&chksm=bd2537528a52be441263578bd7c1f33dbdc65d1a3459de3522bfd03182bd387a86c8fe506754&scene=0#rd


Mybatis的二级缓存相对于一级缓存来说，实现了SqlSession之间缓存数据的共享，
同时粒度更加的细，能够到Mapper级别，通过Cache接口实现类不同的组合，对Cache的可控性也更强。


Mybatis在多表查询时，极大可能会出现脏数据，有设计上的缺陷，安全使用的条件比较苛刻。


在分布式环境下，由于默认的Mybatis Cache实现都是基于本地的，分布式环境下必然会出现读取到
脏数据，需要使用集中式缓存将Mybatis的Cache接口实现，有一定的开发成本，不如直接用Redis，
Memcache实现业务上的缓存就好了。