export class FoodQuery {
  constructor(
    public name?: string,
    public description?: string,
    public calories?: number,
    public limit?: number
  ) {}
}
