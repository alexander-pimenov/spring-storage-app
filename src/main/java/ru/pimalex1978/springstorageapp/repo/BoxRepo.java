package ru.pimalex1978.springstorageapp.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pimalex1978.springstorageapp.entity.Box;

/**
 * BoxRepo - это расширенный (extends) интерфейс из CrudRepository<Box, Long>.
 * Spring Data JPA сам создаст для вас класс применения (implements) данного
 * интерфейса во время старта приложения.
 * <p>
 * Это будет автоматически реализовано Spring в bean-компонент с именем boxRepo
 * CRUD ссылается на создание, чтение, обновление, удаление.
 */


public interface BoxRepo extends CrudRepository<Box, Long> {
}
