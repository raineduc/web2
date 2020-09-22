
export class Point {
    /**
     * @param {number} x - X coordinate
     * @param {number} y - Y coordinate
     */
    constructor(x, y) {
        this.y = y;
        this.x = x;
    }

    /**
     * @returns {number} X
     */
    getX() {
        return this.x;
    }

    /**
     * @returns {number} Y
     */
    getY() {
        return this.y;
    }
}