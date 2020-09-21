<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Web 1</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>
<header class="header">
  <span class="name">Хузин Рамиль Ришатович</span>
  <span class="group">Группа: P3212</span>
  <span class="option">Вариант: 2326</span>
</header>
<div class="main">
  <div class="game-interact">
    <form class="game-form" method="POST">
      <div class="game-form__field">
        <label for="x-coord" class="game-form__label">Введите X координату: </label>
        <div class="input-wrapper game-form__input-wrapper">
          <select id="x-coord" name="x-coord" class="des-input des-select game-form__coord-select">
            <option value="-2">-2</option>
            <option value="-1.5">-1,5</option>
            <option value="-1">-1</option>
            <option value="-0.5">-0,5</option>
            <option value="0">0</option>
            <option value="0.5">0,5</option>
            <option value="1">1</option>
            <option value="1.5">1,5</option>
            <option value="2">2</option>
          </select>
        </div>
      </div>
      <div class="game-form__field">
        <label for="y-coord" class="game-form__label">Введите Y координату в пределах (-5,3):</label>
        <div class="input-wrapper game-form__input-wrapper">
          <input type="number" id="y-coord" name="y-coord" class="des-input game-form__coord" step="any">
        </div>
      </div>
      <div class="game-form__field">
        <span class="game-form__label">Введите параметр R: </span>
        <div class="input-wrapper game-form__input-wrapper">
          <div class="game-form__radio-group">
            <label class="game-form__radius-label">1 <input type="checkbox" name="r-param" value="1"
                                                            class="des-input game-form__radius"></label>
            <label class="game-form__radius-label">1,5 <input type="checkbox" name="r-param" value="1.5"
                                                            class="des-input game-form__radius"></label>
            <label class="game-form__radius-label">2 <input type="checkbox" name="r-param" value="2"
                                                            class="des-input game-form__radius"></label>
            <label class="game-form__radius-label">2,5 <input type="checkbox" name="r-param" value="2.5"
                                                            class="des-input game-form__radius"></label>
            <label class="game-form__radius-label">3 <input type="checkbox" name="r-param" value="3"
                                                            class="des-input game-form__radius"></label>
          </div>
        </div>
      </div>
      <button type="submit" class="des-button game-form__submit-button">Отправить</button>
    </form>
    <div class="game-area">
      <img src="${pageContext.request.contextPath}/static/area.png" alt="area" class="game-area__image">
    </div>
  </div>
  <div class="game-results"></div>
</div>
<script src="${pageContext.request.contextPath}/js/index.js" type="module"></script>
</body>
</html>