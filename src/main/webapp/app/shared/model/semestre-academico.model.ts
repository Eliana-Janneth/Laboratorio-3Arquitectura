import { IGrupo } from 'app/shared/model/grupo.model';

export interface ISemestreAcademico {
  id?: number;
  semestreAcademicoId?: number;
  codigo?: string;
  estado?: boolean;
  anio?: number;
  periodoAcademico?: number;
  grupos?: IGrupo[] | null;
}

export const defaultValue: Readonly<ISemestreAcademico> = {
  estado: false,
};
