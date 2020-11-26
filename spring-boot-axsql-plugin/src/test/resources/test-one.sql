-- Обновим имена
-- name: updateNamed!
-- @param long id
-- @param String name
-- @param java.utils.Integer k
-- @param AxTestEnum enum .getInteger()
-- @mapper com.axgrid.MyMapper
update test set
                name=?,     -- имя
                k=?,        -- k
                enum=?      -- флаг
where id=?; -- ID игрока



