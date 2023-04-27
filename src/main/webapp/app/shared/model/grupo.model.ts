import { IGrupoEstudiante } from 'app/shared/model/grupo-estudiante.model';
import { IActividad } from 'app/shared/model/actividad.model';
import { ICurso } from 'app/shared/model/curso.model';
import { ISemestreAcademico } from 'app/shared/model/semestre-academico.model';
import { IProfesor } from 'app/shared/model/profesor.model';

export interface IGrupo {
  id?: number;
  grupoId?: number;
  codigo?: string;
  horario?: string | null;
  estudiantes?: IGrupoEstudiante[] | null;
  actividades?: IActividad[] | null;
  curso?: ICurso | null;
  semestreAcademico?: ISemestreAcademico | null;
  profesors?: IProfesor[] | null;
}

export const defaultValue: Readonly<IGrupo> = {};
