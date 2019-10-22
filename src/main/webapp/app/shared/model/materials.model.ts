export interface IMaterials {
  id?: number;
  materialName?: string;
}

export class Materials implements IMaterials {
  constructor(public id?: number, public materialName?: string) {}
}
