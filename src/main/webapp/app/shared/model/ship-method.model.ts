export interface IShipMethod {
  id?: number;
  shipMethodName?: string;
}

export class ShipMethod implements IShipMethod {
  constructor(public id?: number, public shipMethodName?: string) {}
}
