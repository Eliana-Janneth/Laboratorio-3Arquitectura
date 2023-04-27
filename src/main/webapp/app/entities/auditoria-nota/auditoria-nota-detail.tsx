import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './auditoria-nota.reducer';

export const AuditoriaNotaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const auditoriaNotaEntity = useAppSelector(state => state.auditoriaNota.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="auditoriaNotaDetailsHeading">
          <Translate contentKey="labArquiJHipsterApp.auditoriaNota.detail.title">AuditoriaNota</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{auditoriaNotaEntity.id}</dd>
          <dt>
            <span id="auditoriaId">
              <Translate contentKey="labArquiJHipsterApp.auditoriaNota.auditoriaId">Auditoria Id</Translate>
            </span>
          </dt>
          <dd>{auditoriaNotaEntity.auditoriaId}</dd>
          <dt>
            <span id="notaAnterior">
              <Translate contentKey="labArquiJHipsterApp.auditoriaNota.notaAnterior">Nota Anterior</Translate>
            </span>
          </dt>
          <dd>{auditoriaNotaEntity.notaAnterior}</dd>
          <dt>
            <span id="notaNueva">
              <Translate contentKey="labArquiJHipsterApp.auditoriaNota.notaNueva">Nota Nueva</Translate>
            </span>
          </dt>
          <dd>{auditoriaNotaEntity.notaNueva}</dd>
          <dt>
            <span id="fechaModificacion">
              <Translate contentKey="labArquiJHipsterApp.auditoriaNota.fechaModificacion">Fecha Modificacion</Translate>
            </span>
          </dt>
          <dd>
            {auditoriaNotaEntity.fechaModificacion ? (
              <TextFormat value={auditoriaNotaEntity.fechaModificacion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ip">
              <Translate contentKey="labArquiJHipsterApp.auditoriaNota.ip">Ip</Translate>
            </span>
          </dt>
          <dd>{auditoriaNotaEntity.ip}</dd>
          <dt>
            <span id="usuarioPortal">
              <Translate contentKey="labArquiJHipsterApp.auditoriaNota.usuarioPortal">Usuario Portal</Translate>
            </span>
          </dt>
          <dd>{auditoriaNotaEntity.usuarioPortal}</dd>
          <dt>
            <Translate contentKey="labArquiJHipsterApp.auditoriaNota.profesor">Profesor</Translate>
          </dt>
          <dd>{auditoriaNotaEntity.profesor ? auditoriaNotaEntity.profesor.id : ''}</dd>
          <dt>
            <Translate contentKey="labArquiJHipsterApp.auditoriaNota.nota">Nota</Translate>
          </dt>
          <dd>{auditoriaNotaEntity.nota ? auditoriaNotaEntity.nota.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/auditoria-nota" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/auditoria-nota/${auditoriaNotaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuditoriaNotaDetail;
