package ru.pimalex1978.springstorageapp.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pimalex1978.springstorageapp.entity.Item;

import java.util.List;

/**
 * ItemRepo - это расширенный (extends) интерфейс из CrudRepository<Item, Long>.
 * Spring Data JPA сам создаст для вас класс применения (implements) данного
 * интерфейса во время старта приложения.
 * <p>
 * Это будет автоматически реализовано Spring в bean-компонент с именем itemRepo
 * CRUD ссылается на создание, чтение, обновление, удаление.
 */


public interface ItemRepo extends CrudRepository<Item, Long> {

}
