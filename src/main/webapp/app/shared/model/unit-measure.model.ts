export interface IUnitMeasure {
  id?: number;
  unitMeasureCode?: string;
  unitMeasureName?: string;
}

export class UnitMeasure implements IUnitMeasure {
  constructor(public id?: number, public unitMeasureCode?: string, public unitMeasureName?: string) {}
}
