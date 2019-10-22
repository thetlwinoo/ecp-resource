export interface IUploadActionTypes {
  id?: number;
  actionTypeName?: string;
}

export class UploadActionTypes implements IUploadActionTypes {
  constructor(public id?: number, public actionTypeName?: string) {}
}
