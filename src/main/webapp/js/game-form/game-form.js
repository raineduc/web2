import { isNumber, getNumber, isYCoordInValidRange } from "../validation.js";
import { draw as drawGameArea, drawPoint as drawPointInGameArea, translateCanvasCoordsToRCoords } from "../game-area/game-area.js";
import { Point } from "../game-area/point.js";

const gameForm = document.documentElement.querySelector(".game-form");
const tableWrapper = document.documentElement.querySelector(".game-results");

const coordInput = gameForm.querySelector(".game-form__coord");
const coordSelect = gameForm.querySelector(".game-form__coord-select");
const radiusInput = gameForm.querySelector(".game-form__radius");

const coordSelectWrapper = coordSelect.closest(".game-form__input-wrapper");
const coordInputWrapper = coordInput.closest(".game-form__input-wrapper");
const radiusInputWrapper = radiusInput.closest(".game-form__input-wrapper");

const canvas = document.querySelector('.game-area__image');

const allowedXValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];

fetch("./resultsTable", { method: "GET" })
  .then(res => {
    if (res.ok) {
      return res.text();
    }
  })
  .then(data => {
    tableWrapper.innerHTML = data;
  });

drawGameArea(Array.from(document.querySelectorAll(".game-form__radius:checked")).map(
    radiusInput => Number(radiusInput.value)
));

gameForm.addEventListener("submit", async (e) => {
  e.preventDefault();

  const checkedRadiusInput = gameForm.querySelector(
    ".game-form__radius:checked"
  );

  const error = [
    validateRadius(
      radiusInputWrapper,
      checkedRadiusInput && checkedRadiusInput.value
    ),
    validateYCoordinate(coordInputWrapper, coordInput.value),
    validateXCoordinate(coordSelectWrapper, coordSelect.value),
  ].some(value => !value);

  if (error) return;

  const response = await fetch("./results", {
    method: "POST",
    body: new FormData(gameForm),
  });

  if (response.ok) {
    // const html = await response.text();
    // tableWrapper.innerHTML = html;
    window.location.href = "/results";
  } else {
    const error = await response.text();
    alert(`Ошибка ${response.status} ${error ? `: ${error}` : ""}`);
  }
});

Array.from(
  document.documentElement.querySelectorAll(".game-form__radius")
).forEach((input) => {
  input.addEventListener("change", (e) => {
    if (input.checked) removeError(input.closest(".game-form__input-wrapper"));
    const radiusInputs = Array.from(document.querySelectorAll(".game-form__radius:checked")).map(
        radiusInput => Number(radiusInput.value)
    );
    drawGameArea(radiusInputs);
  });
});

coordInput.addEventListener("blur", (e) => {
  if (hasError(coordInputWrapper)) {
    removeError(coordInputWrapper);
    validateYCoordinate(coordInputWrapper, coordInput.value);
  }
});

const coordInputHandler = () => {
  if (coordInput.value.search(/[\.\,]\d{6,}/) !== -1) {
    const str = `${parseFloat(coordInput.value).toFixed(6)}`;
    coordInput.value = str.slice(0, str.length - 1);
  }
};

coordInput.addEventListener("input", coordInputHandler);

coordSelect.addEventListener("change", (e) => {
  if (coordSelect.value) {
    removeError(coordInputWrapper);
  }
});

canvas.addEventListener("click", event => {
  const radiusInputs = Array.from(document.querySelectorAll(".game-form__radius:checked")).map(
      radiusInput => Number(radiusInput.value)
  );
  if (radiusInputs.length !== 0) {
    const point = new Point(event.offsetX, event.offsetY);
    drawGameArea(radiusInputs, point);
    const pointInArea = translateCanvasCoordsToRCoords(point, Math.max(...radiusInputs));
    coordInput.value = String(pointInArea.getY());
    const nearestAllowedX = allowedXValues.reduce((acc, x) =>
        Math.abs(x - pointInArea.getX()) < Math.abs(acc - pointInArea.getX()) ? x : acc, Infinity);
    document.querySelector(`.game-form__coord-button[data-value="${nearestAllowedX}"]`).click();
    coordInputHandler();
  } else {
    alert("Не выбран радиус");
  }
});

const coordButtons = document.querySelectorAll(".game-form__coord-button");

Array.from(coordButtons).forEach(button => {
  button.addEventListener("click", e => {
    coordButtons.forEach(but => but.classList.remove("game-form__coord-button_checked"));
    button.classList.add("game-form__coord-button_checked");
    coordSelect.value = parseFloat(button.getAttribute("data-value"));
    removeError(coordSelectWrapper);
  });
});

const addError = (wrapper, message) => {
  wrapper.classList.add("game-form__input-wrapper_error");
  const error =
    wrapper.querySelector(".game-form__input-error") ||
    document.createElement("p");
  if (!wrapper.querySelector(".game-form__input-error")) {
    error.className = "game-form__input-error";
    wrapper.appendChild(error);
  }
  error.textContent = message;
};

const removeError = (wrapper) => {
  wrapper.classList.remove("game-form__input-wrapper_error");
  const error = wrapper.querySelector(".game-form__input-error");
  if (error) error.remove();
};

const hasError = (wrapper) => {
  return Boolean(wrapper.querySelector(".game-form__input-error"));
}

const validateYCoordinate = (wrapper, value) => {
  if (isNumber(value)) {
    const num = getNumber(value);
    if (isYCoordInValidRange(num)) {
      removeError(wrapper);
      return true;
    }
    addError(wrapper, "Координата Y должна быть в пределах (-5, 3)");
    return false;
  }
  addError(wrapper, "В поле введено не число");
  return false;
};

const validateXCoordinate = (wrapper, value) => {
  if (value) {
    removeError(wrapper);
    return true;
  }
  addError(wrapper, "Выберите значение X");
  return false;
};

const validateRadius = (wrapper, value) => {
  if (value) {
    removeError(wrapper);
    return true;
  }
  addError(wrapper, "Выберите значение радиуса R");
  return false;
};
