
-- Создадим базу
-- name: ddl!
create table if not exists test
(
    id bigint auto_increment not null, -- ID
    name varchar(100),                 -- Имя
    k int,                             -- Чистовое поле
    enum int                           -- Enum
);


-- Создадим запись
-- name: insert<!
insert into test(name, k, enum) values (?,?,?); -- Запись

-- name: update!
update test set name=?, k=?, enum=? where id=?;

-- Обновим имена
-- name: updateNamed!
-- @param long id
-- @param String name
-- @param int k
-- @param AxTestEnum enum .getInteger()
-- @mapper com.axgrid.MyMapper
update test set
        name=?,     -- имя
        k=?,        -- k
        enum=?      -- флаг
        where id=?; -- ID игрока


-- name: updateObject!
-- @object AxTestObject object
update test set name=?, k=?, enum=? where id=?;


