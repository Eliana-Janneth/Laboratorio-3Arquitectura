import dayjs from 'dayjs';
import { IProfesor } from 'app/shared/model/profesor.model';
import { INota } from 'app/shared/model/nota.model';

export interface IAuditoriaNota {
  id?: number;
  auditoriaId?: number;
  notaAnterior?: number;
  notaNueva?: number;
  fechaModificacion?: string;
  ip?: string;
  usuarioPortal?: string;
  profesor?: IProfesor | null;
  nota?: INota | null;
}

export const defaultValue: Readonly<IAuditoriaNota> = {};
