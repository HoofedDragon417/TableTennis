# Приложение для отслеживаеия текущего счёта во время игры в настольный теннис.

Сделан в целях освоения MVVM. Использует [Cicerone](https://github.com/terrakok/Cicerone) для навигации по приложению,
сохраняет статистику игроков в локальную SQLite БД.

Приложение поддерживает игру как один на один, так и два на два.

**Главный экран.**

На главном экране находятся кнопки навигации между экранами:
- экран выбора игроков для игры один на один;
- экран выбора игроков для игры два на два;
- экран статистики игроков за всё время.

Также там находится кнопка выхода из приложения.

**Экран выбора игроков.**

По нажатию на игрока из списка доступных он выбирается как учавствующий в игре.
Если необходимо заменить одного игрока на другого, зажмите его аватарку в верхней части экрана.
При нажатии на аватарку будет совершён переход на экран игры, нажатый игрок будет подавать.

**Экран игры.**

Управление игрой происходит следующим образом:
- нажатие на поле первого игрока (сверху) увеличивает его счёт на единицу;
- зажатие на поле первого игрока умешьшает счёт на единицу:
- аналогично работает для второго игрока (поле снизу)
- нажание на имя (имена) игрока (игроков) заканчивает игру, также он засчитывается как победитель;

**Экран счёта.**

Отображает имя игрока, его аватарку, количество побед и поражений, а также его винрейт.
Винрейт принимает значения от 0 до 1 включительно.